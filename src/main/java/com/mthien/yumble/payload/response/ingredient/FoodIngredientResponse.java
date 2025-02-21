package com.mthien.yumble.payload.response.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodIngredientResponse {
    private String id;
    private String ingredientName;
    private Float usage;
    private Boolean isCore;
}
