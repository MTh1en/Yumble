package com.mthien.yumble.payload.response.step;

import com.mthien.yumble.payload.response.food.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StepDetailResponse {
    private String id;
    private FoodResponse food;
    private String image;
    private Integer stepOrder;
    private String description;
}
