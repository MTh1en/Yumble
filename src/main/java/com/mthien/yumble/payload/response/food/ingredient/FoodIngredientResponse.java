package com.mthien.yumble.payload.response.food.ingredient;

import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodIngredientResponse {
    private FoodResponse food;
    private IngredientResponse ingredient;
    private Float usage;
    private Boolean isCore;
}
