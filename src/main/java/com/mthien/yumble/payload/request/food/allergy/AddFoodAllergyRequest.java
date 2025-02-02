package com.mthien.yumble.payload.request.food.allergy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFoodAllergyRequest {
    private String allergyId;
}
