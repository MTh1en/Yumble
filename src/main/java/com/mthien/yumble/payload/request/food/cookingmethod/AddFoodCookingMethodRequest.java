package com.mthien.yumble.payload.request.food.cookingmethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFoodCookingMethodRequest {
    private String cookingMethodId;
    private Float timeRequired;
    private Boolean isCore;
}
