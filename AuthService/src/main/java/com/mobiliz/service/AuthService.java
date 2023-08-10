package com.mobiliz.service;

import com.mobiliz.dto.request.*;
import com.mobiliz.dto.response.LoginResponseDto;
import com.mobiliz.exception.AuthManagerException;
import com.mobiliz.exception.ErrorType;
import com.mobiliz.manager.IUserManager;
import com.mobiliz.mapper.IAuthMapper;
import com.mobiliz.rabbitmq.model.ForgotPasswordMailModel;
import com.mobiliz.rabbitmq.producer.ForgotPasswordMailProducer;
import com.mobiliz.rabbitmq.producer.MailProducer;
import com.mobiliz.repository.IAuthRepository;
import com.mobiliz.repository.entity.Auth;
import com.mobiliz.repository.entity.enums.EStatus;
import com.mobiliz.utility.CodeGenerator;
import com.mobiliz.utility.JwtTokenProvider;
import com.mobiliz.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserManager userManager;
    private final JwtTokenProvider tokenProvider;
    private final RoleService roleService;
    private final MailProducer mailProducer;
    private final ForgotPasswordMailProducer forgotPasswordMailProducer;

    public AuthService(IAuthRepository authRepository, PasswordEncoder passwordEncoder,
                       IUserManager userManager, JwtTokenProvider tokenProvider,
                       RoleService roleService, MailProducer mailProducer,
                       ForgotPasswordMailProducer forgotPasswordMailProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.userManager = userManager;
        this.tokenProvider = tokenProvider;
        this.roleService = roleService;
        this.mailProducer = mailProducer;
        this.forgotPasswordMailProducer = forgotPasswordMailProducer;
    }

    public Boolean register(RegisterRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmail(dto.getEmail());
        if (optionalAuth.isPresent())
            throw new AuthManagerException(ErrorType.EMAIL_DUPLICATE);
        Auth auth = IAuthMapper.INSTANCE.fromRegisterRequestDtoToAuth(dto);
        List<Long> roleList = new ArrayList<>();
        // TODO company idlere göre roller dağıtılacak
        Long companyId = -1L;
        roleList.add(1L);
        auth.setRoleIds(roleList);
        auth.setEStatus(EStatus.ACTIVE);
        roleList.add(2L);
        roleList.add(3L);
        auth.setRoleIds(roleList);
        //companyId = companyManager.getCompanyIdWithCompanyName(dto.getCompanyName()).getBody();

        if (dto.getPassword().equals(dto.getRepassword())) {
            auth.setActivateCode(CodeGenerator.generateCode());
            auth.setPassword(passwordEncoder.encode(dto.getPassword()));
            if(companyId == 0)
                auth.setEStatus(EStatus.BANNED);
            Auth savedAuth = save(auth);
            FromAuthToUserProfileCreateUserRequestDto createUserRequestDto = IAuthMapper.INSTANCE.fromAuthToCreateUserRequestDto(savedAuth);
            createUserRequestDto.setCompanyId(companyId);
            userManager.createUser(createUserRequestDto);
            if(auth.getRoleIds().contains(2L) && !auth.getEStatus().equals(EStatus.BANNED)){
                mailProducer.sendActivationLink(IAuthMapper.INSTANCE.fromAuthToRegisterMailModel(savedAuth));
            }
            return true;
        } else {
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        }
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmail(dto.getEmail());
        if (optionalAuth.isEmpty() || !passwordEncoder.matches(dto.getPassword(), optionalAuth.get().getPassword()))
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        else if (optionalAuth.get().getEStatus().equals(EStatus.BANNED))
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR_MANAGER);
        else if (optionalAuth.get().getEStatus().equals(EStatus.INACTIVE))
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR_MANAGER2);
        else if (!optionalAuth.get().getEStatus().equals(EStatus.ACTIVE))
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        List<String> roles = new ArrayList<>();
        optionalAuth.get().getRoleIds().forEach(roleId -> {
            roles.add(roleService.getRoleNames(roleId));
        });
        Optional<String> token = tokenProvider.createToken(optionalAuth.get().getAuthId(),optionalAuth.get().getCompanyId(), roles);
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .token(token.get())
                .roles(tokenProvider.getRolesFromToken(token.get()))
                .build();
        // if(loginResponseDto.getRoles().contains("MANAGER"))
            //loginResponseDto.setCompanyId(companyManager.getCompanyIdWithCompanyName(optionalAuth.get().getCompanyName()).getBody());
        return loginResponseDto;
    }

    public Boolean activateStatus(String token) {
        Optional<Long> authId = tokenProvider.getIdFromToken(token);
        if (authId.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        Optional<Auth> optionalAuth = findById(authId.get());
        if (optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        if (optionalAuth.get().getRoleIds().contains(2L)) {
            optionalAuth.get().setEStatus(EStatus.INACTIVE);
            update(optionalAuth.get());
            FromAuthToUserProfileActivateManagerStatusRequestDto dto = FromAuthToUserProfileActivateManagerStatusRequestDto.builder()
                    .authId(optionalAuth.get().getAuthId())
                    .status(optionalAuth.get().getEStatus())
                    .build();
            userManager.activateManagerStatus(dto);
        }
        return true;
    }

    public Boolean doActivateManager(FromUserProfileToAuthForActivateManager dto) {
        Optional<Auth> auth = findById(dto.getAuthId());
        if (auth.isEmpty()) throw new RuntimeException("Kullanıcı yok");
        auth.get().setEStatus(dto.getStatus());
        update(auth.get());
        return true;
    }

    public Boolean forgotPassword(String email, String name){
        Optional<Auth> auth = authRepository.findOptionalByEmail(email);
        if (auth.get().getEStatus().equals(EStatus.ACTIVE)){
            if (auth.get().getName().equals(name)){
                String randomPassword = UUID.randomUUID().toString();
                auth.get().setPassword(passwordEncoder.encode(randomPassword));
                save(auth.get());
                System.out.println(randomPassword);
                ForgotPasswordMailModel model = ForgotPasswordMailModel.builder()
                        .authId(auth.get().getAuthId())
                        .password(randomPassword)
                        .email(email)
                        .build();
                forgotPasswordMailProducer.sendForgotPasswordMail(model);
                UserprofileChangePasswordRequestDto changePasswordRequestDto = UserprofileChangePasswordRequestDto.builder()
                        .authId(auth.get().getAuthId()).password(auth.get().getPassword()).build();
                userManager.forgotPasswordUser(changePasswordRequestDto);
                return true;
            }else {
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            }
        }else {
            if (auth.get().getEStatus().equals(EStatus.DELETED)){
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            }
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public Boolean updateAuth(String token,UserUpdateRequestDto dto ) {
        Optional<Long> authId=tokenProvider.getIdFromToken(token);
        if (authId.isEmpty()){
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<Auth> auth=findById(authId.get());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        update(IAuthMapper.INSTANCE.fromUserUpdateRequestDtotoAuth(auth.get(),dto));
        return  true;
    }

    public Boolean deleteAuth(Long authId) {
        Optional<Auth> optionalAuth = findById(authId);
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        optionalAuth.get().setEStatus(EStatus.DELETED);
        update(optionalAuth.get());
        return true;
    }

}
