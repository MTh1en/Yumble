package com.mthien.yumble.payload.request.food;

import com.mthien.yumble.entity.Enum.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateFoodRequest {
    private String name;
    private String description;
    private Meal meal;
    private String cookingMethod;
}
