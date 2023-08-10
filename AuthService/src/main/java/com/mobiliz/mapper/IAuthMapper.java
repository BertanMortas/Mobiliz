package com.mobiliz.mapper;

import com.mobiliz.dto.request.*;
import com.mobiliz.dto.response.RegisterResponseDto;
import com.mobiliz.rabbitmq.model.RegisterMailModel;
import com.mobiliz.repository.entity.Auth;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    FromAuthToUserProfileCreateUserRequestDto fromAuthToCreateUserRequestDto(final Auth auth);

    Auth fromRegisterRequestDtoToAuth(final RegisterRequestDto dto);

    RegisterMailModel fromAuthToRegisterMailModel(final Auth auth);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Auth fromUserUpdateRequestDtotoAuth(@MappingTarget Auth auth, final UserUpdateRequestDto dto);
}
