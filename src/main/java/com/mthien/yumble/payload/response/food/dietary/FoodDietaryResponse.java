package com.mthien.yumble.payload.response.food.dietary;

import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDietaryResponse {
    private DietaryResponse dietary;
}
