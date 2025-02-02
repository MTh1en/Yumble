package com.mthien.yumble.payload.response.food.dietary;

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
public class FoodDietaryDetailResponse {
    private FoodResponse food;
    private DietaryResponse dietary;
}
