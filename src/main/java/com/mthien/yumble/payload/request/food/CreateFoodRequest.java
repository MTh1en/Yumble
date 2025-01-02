package com.mthien.yumble.payload.request.food;

import com.mthien.yumble.entity.Enum.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFoodRequest {
    private String name;
    private String description;
    private Meal meal;
    private Set<String> allergies;
    private Set<String> dietaries;
    private Set<String> methodCooking;
}
