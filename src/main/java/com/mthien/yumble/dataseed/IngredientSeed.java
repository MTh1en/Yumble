package com.mthien.yumble.dataseed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientSeed {
    private String name;
    private Float usage;
    private Boolean isCore;
}
