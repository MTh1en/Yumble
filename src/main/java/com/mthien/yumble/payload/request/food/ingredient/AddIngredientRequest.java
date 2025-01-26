package com.mthien.yumble.payload.request.food.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddIngredientRequest {
    private String ingredientId;
    private Float usage;
    private Boolean isCore;
}
