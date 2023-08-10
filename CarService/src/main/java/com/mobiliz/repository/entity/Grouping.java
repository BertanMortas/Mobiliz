package com.mobiliz.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Grouping extends Base{
    /**
     * todo, we can use that fields on other entities, if i have time i will separate them
     */
    @Id
    private String groupingId;
    private String country;
    private String fleet;
    private String group;
    private List<String> carIds = new ArrayList<>();
    private String subGroupId;
    @Builder.Default
    private Boolean isSubGroup = false;
}
