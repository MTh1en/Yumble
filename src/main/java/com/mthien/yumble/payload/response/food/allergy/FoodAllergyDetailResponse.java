package com.mthien.yumble.payload.response.food.allergy;

import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodAllergyDetailResponse {
    private FoodResponse food;
    private AllergyResponse allergy;
}
