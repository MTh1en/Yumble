package com.mthien.yumble.payload.response.food;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Enum.Meal;
import com.mthien.yumble.entity.MethodCooking;
import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import com.mthien.yumble.payload.response.methodcooking.MethodCookingResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse {
    private String id;
    private String image;
    private String name;
    private String description;
    private Meal meal;
    private Set<AllergyResponse> allergies;
    private Set<DietaryResponse> dietaries;
    private Set<MethodCookingResponse> methodCooking;
}
