package com.mobiliz.repository.entity.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarFindAllView {
    // todo id is for development delete after
    private String id;
    private String plate;
    private String tag;
    private Long companyId;
}
