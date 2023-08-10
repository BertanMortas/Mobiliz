package com.mobiliz.mapper;

import com.mobiliz.dto.request.CreateGroupingRequestDto;
import com.mobiliz.dto.request.CreateSubGroupingRequestDto;
import com.mobiliz.dto.request.UpdateGroupingRequestDto;
import com.mobiliz.repository.entity.Grouping;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IGroupingMapper {
    IGroupingMapper INSTANCE = Mappers.getMapper(IGroupingMapper.class);
    Grouping toGrouping(final CreateGroupingRequestDto dto);
    Grouping toGrouping(final CreateSubGroupingRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Grouping toGrouping(@MappingTarget Grouping grouping, final UpdateGroupingRequestDto dto);
}
