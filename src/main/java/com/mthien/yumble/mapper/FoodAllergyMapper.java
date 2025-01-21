package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodAllergy;
import com.mthien.yumble.payload.response.foodallergy.FoodAllergyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodAllergyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "food", expression = "java(food)")
    @Mapping(target = "allergy", expression = "java(allergy)")
    @Mapping(target = "severity", expression = "java(severity)")
    FoodAllergy createFoodAllergy(Food food, Allergy allergy, String severity);

    FoodAllergyResponse toFoodAllergyResponse(FoodAllergy foodAllergy);
}
