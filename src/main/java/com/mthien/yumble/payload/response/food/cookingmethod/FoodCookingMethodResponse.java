package com.mthien.yumble.payload.response.food.cookingmethod;

import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodCookingMethodResponse {
    private CookingMethodResponse cookingMethod;
    private Float timeRequired;
    private Boolean isCore;
}
