package com.mthien.yumble.payload.response.fooddietary;

import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDietaryResponse {
    private FoodResponse food;
    private DietaryResponse dietary;
    private String priority;
}
