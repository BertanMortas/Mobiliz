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
public class UpdatePermissionRequestDto {
    private String token;
    private Long permissionId;
    private EPermissionType permissionType;
}
