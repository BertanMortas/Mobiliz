package com.mobiliz.mapper;

import com.mobiliz.dto.request.CreatePermissionRequestDto;
import com.mobiliz.dto.request.UpdatePermissionRequestDto;
import com.mobiliz.repository.entity.Permission;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPermissionMapper {
    IPermissionMapper INSTANCE = Mappers.getMapper(IPermissionMapper.class);
    Permission toPermission(final CreatePermissionRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Permission toPermission(@MappingTarget Permission permission, final UpdatePermissionRequestDto dto);
}
