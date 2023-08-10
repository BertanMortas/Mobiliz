package com.mobiliz.repository.entity;

import com.mobiliz.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Car extends Base{
    @Id
    private String carId;
    private String plate;
    private String brand;
    private String model;
    private Long modelYear;
    private Long chassisNo;
    private String tag;
    private Long companyId; // comes from the token
    @Builder.Default
    private EStatus status = EStatus.ACTIVE;
}
