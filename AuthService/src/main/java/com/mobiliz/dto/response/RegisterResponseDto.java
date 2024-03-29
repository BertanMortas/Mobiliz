package com.mobiliz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDto {
    private String email;
    private String name;
    private String surname;
    private String activateCode;
}
