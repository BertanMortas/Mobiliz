package com.mobiliz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGroupingRequestDto {
    private String token;
    private String groupingId;
    private String country;
    private String fleet;
    private String group;
    private List<String> carIds;
}
