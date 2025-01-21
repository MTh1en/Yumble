package com.mthien.yumble.payload.response.nutrition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionResponse {
    private String id;
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
