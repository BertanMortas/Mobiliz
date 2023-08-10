package com.mobiliz.dto.request;

import com.mobiliz.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FromAuthToUserProfileActivateManagerStatusRequestDto {
    private Long authId;
    private EStatus status;
}
