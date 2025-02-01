package com.mthien.yumble.payload.response.ingredient;

import com.mthien.yumble.payload.response.food.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientResponse {
    private String id;
    private FoodResponse food;
    private String ingredientName;
    private Float usage;
    private Boolean isCore;
}
