package com.mobiliz.service;

import com.mobiliz.dto.request.CreateCarAuthRequestDto;
import com.mobiliz.exception.CarAuthManagerException;
import com.mobiliz.exception.ErrorType;
import com.mobiliz.mapper.ICarAuthMapper;
import com.mobiliz.repository.ICarAuthRepository;
import com.mobiliz.repository.entity.CarAuth;
import com.mobiliz.utility.JwtTokenProvider;
import com.mobiliz.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CarAuthService extends ServiceManager<CarAuth,Long> {
    private final ICarAuthRepository carAuthRepository;
    private final PermissionService permissionService;
    public CarAuthService(ICarAuthRepository carAuthRepository, PermissionService permissionService) {
        super(carAuthRepository);
        this.carAuthRepository = carAuthRepository;
        this.permissionService = permissionService;
    }
    public CarAuth Create(CreateCarAuthRequestDto dto){
        Long companyId = permissionService.tokenRoleControls(dto.getToken());
        if (!permissionService.findById(dto.getPermissionId()).get().getAuthId().equals(dto.getManagerAuthId())) {
            throw new CarAuthManagerException(ErrorType.INVALID_PERMISSION);
        }
        CarAuth carAuth = ICarAuthMapper.INSTANCE.toCarAuth(dto);
        carAuth.setCompanyId(companyId);
        return save(carAuth);
    }

}
