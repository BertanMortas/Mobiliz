package com.mobiliz.mapper;

import com.mobiliz.dto.request.*;
import com.mobiliz.dto.response.GetNameByIdResponseDto;
import com.mobiliz.repository.entity.UserProfile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    UserProfile createUser(final CreateUserRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile fromUpdateUserProfileToUserProfile(@MappingTarget UserProfile userProfile, final UserUpdateRequestDto dto);

}
