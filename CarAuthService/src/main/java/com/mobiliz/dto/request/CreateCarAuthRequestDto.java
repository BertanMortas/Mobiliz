package com.mobiliz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarAuthRequestDto {
    private String token;
    private String carId;
    private String groupingId;
    private Long permissionId;
    private Long managerAuthId;
}
