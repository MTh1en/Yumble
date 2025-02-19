package com.mthien.yumble.payload.response.food;

import com.mthien.yumble.entity.Enum.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse implements Serializable {
    private String id;
    private String image;
    private String name;
    private String description;
    private Meal meal;
    private String cookingMethod;
}
