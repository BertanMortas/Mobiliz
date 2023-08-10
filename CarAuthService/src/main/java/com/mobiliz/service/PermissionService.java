package com.mobiliz.service;

import com.mobiliz.dto.request.CreatePermissionRequestDto;
import com.mobiliz.dto.request.DeletePermissionRequestDto;
import com.mobiliz.dto.request.UpdatePermissionRequestDto;
import com.mobiliz.exception.CarAuthManagerException;
import com.mobiliz.exception.ErrorType;
import com.mobiliz.mapper.IPermissionMapper;
import com.mobiliz.repository.IPermissionRepository;
import com.mobiliz.repository.entity.Permission;
import com.mobiliz.utility.JwtTokenProvider;
import com.mobiliz.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService extends ServiceManager<Permission,Long> {
    private final IPermissionRepository permissionRepository;
    private final JwtTokenProvider tokenProvider;
    public PermissionService(IPermissionRepository permissionRepository, JwtTokenProvider tokenProvider) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
        this.tokenProvider = tokenProvider;
    }
    public Permission create(CreatePermissionRequestDto dto){
        tokenRoleControls(dto.getToken());
        return save(IPermissionMapper.INSTANCE.toPermission(dto));
    }
    public Permission update(UpdatePermissionRequestDto dto){
        tokenRoleControls(dto.getToken());
        Optional<Permission> permission = findById(dto.getPermissionId());
        if (permission.isEmpty()) {
            throw new CarAuthManagerException(ErrorType.CAR_AUTH_NOT_FOUND);
        }
        Permission permissionUpdated = IPermissionMapper.INSTANCE.toPermission(permission.get(),dto);
        return update(permissionUpdated);
    }
    public Boolean delete(DeletePermissionRequestDto dto){
        tokenRoleControls(dto.getToken());
        deleteById(dto.getPermissionId());
        return true;
    }
    public Long tokenRoleControls(String token){
        Optional<Long> companyId = tokenProvider.getCompanyIdFromToken(token);
        if (companyId.isEmpty()) {
            throw new CarAuthManagerException(ErrorType.INVALID_TOKEN);
        }
        List<String> roles = tokenProvider.getRolesFromToken(token);
        if (!roles.contains("COMPANY_ADMIN")) {
            throw new CarAuthManagerException(ErrorType.INVALID_ROLE);
        }
        return companyId.get();
    }
}
