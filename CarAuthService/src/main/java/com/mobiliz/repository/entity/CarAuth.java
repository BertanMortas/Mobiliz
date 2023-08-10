package com.mobiliz.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarAuth extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carId;
    private String groupingId;
    private Long permissionId;
    private Long managerAuthId; // yetki verilecek kişi
    private Long companyId; // arabaların company id leri ile karşılaştırılabilir tam emin değilim
}
