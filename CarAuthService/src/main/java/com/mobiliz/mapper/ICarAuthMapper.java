package com.mobiliz.mapper;

import com.mobiliz.dto.request.CreateCarAuthRequestDto;
import com.mobiliz.dto.request.UpdateCarAuthRequestDto;
import com.mobiliz.repository.entity.CarAuth;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICarAuthMapper {
    ICarAuthMapper INSTANCE = Mappers.getMapper(ICarAuthMapper.class);
    CarAuth toCarAuth(final CreateCarAuthRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarAuth toCarAuth(@MappingTarget CarAuth carAuth, final UpdateCarAuthRequestDto dto);
}
