package com.mthien.yumble.payload.request.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateIngredientRequest {
    private String ingredientName;
    private Float usage;
    private Boolean isCore;
}
