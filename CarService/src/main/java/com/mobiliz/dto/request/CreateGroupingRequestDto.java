package com.mobiliz.dto.request;

import com.mobiliz.repository.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupingRequestDto {
    private String token;
    private String country;
    private String fleet;
    private String group;
    private List<String> carIds;
}
