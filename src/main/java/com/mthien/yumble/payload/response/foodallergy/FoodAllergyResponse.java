package com.mthien.yumble.payload.response.foodallergy;

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
public class FoodAllergyResponse {
    private FoodResponse food;
    private AllergyResponse allergy;
    private String severity;
}
