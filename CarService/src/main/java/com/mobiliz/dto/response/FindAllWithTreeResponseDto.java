package com.mobiliz.dto.response;

import com.mobiliz.repository.entity.Grouping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllWithTreeResponseDto {
    private String country;
    private String fleet;
    private String group;
    private List<String> plates;
    private List<FindAllWithTreeResponseDto> subGroup;
}
