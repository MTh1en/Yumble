package com.mthien.yumble.payload.request.nutrition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateNutritionRequest {
    private Float calories;
    private Float protein;
    private Float fat;
    private Float carb;
    private Float fiber;
    private Float sugar;
    private Float sodium;
    private Float cholesterol;
    private String other;
}
