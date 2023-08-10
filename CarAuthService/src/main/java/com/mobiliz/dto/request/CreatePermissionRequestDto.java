package com.mobiliz.dto.request;

import com.mobiliz.repository.enums.EPermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePermissionRequestDto {
    private String token;
    private Long authId;
    private EPermissionType permissionType;
}
