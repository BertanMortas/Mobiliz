package com.mobiliz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private String surname;
    private String identityNumber;
    private String email;
    private String phoneNumber;
    private Long birthDate;
    private String avatar;

}
