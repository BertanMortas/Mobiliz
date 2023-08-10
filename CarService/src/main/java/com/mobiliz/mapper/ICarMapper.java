package com.mobiliz.mapper;

import com.mobiliz.dto.request.CreateCarRequestDto;
import com.mobiliz.dto.request.UpdateCarRequestDto;
import com.mobiliz.repository.entity.Car;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICarMapper {
    ICarMapper INSTANCE = Mappers.getMapper(ICarMapper.class);
    Car toCar(final CreateCarRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Car fromUpdateCarRequestDtoToCar(@MappingTarget Car car, final UpdateCarRequestDto dto);
}
