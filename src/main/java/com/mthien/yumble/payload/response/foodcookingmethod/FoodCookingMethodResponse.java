package com.mthien.yumble.payload.response.foodcookingmethod;

import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodCookingMethodResponse {
    private FoodResponse food;
    private CookingMethodResponse cookingMethod;
    private Float timeRequired;
    private Boolean isCore;
}
