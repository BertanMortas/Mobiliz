package com.mobiliz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequestDto {
    private String token;
    @NotEmpty
    private String plate;
    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotNull
    private Long modelYear;
    private Long chassisNo;
    private String tag;
}
