package com.mthien.yumble.dataseed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionSeed {
    private float calories;
    private float protein;
    private float fat;
    private float carb;
    private float fiber;
    private float sugar;
    private float sodium;
    private float cholesterol;
}
