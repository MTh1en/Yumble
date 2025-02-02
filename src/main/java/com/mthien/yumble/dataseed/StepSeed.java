package com.mthien.yumble.dataseed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepSeed {
    private Integer order;
    private String description;
    private String image;
}
