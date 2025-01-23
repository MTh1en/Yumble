package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodDietary;
import com.mthien.yumble.payload.response.fooddietary.FoodDietaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodDietaryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "food", expression = "java(food)")
    @Mapping(target = "dietary", expression = "java(dietary)")
    @Mapping(target = "priority", expression = "java(priority)")
    FoodDietary createFoodDietary(Food food, Dietary dietary, String priority);

    FoodDietaryResponse toFoodDietaryResponse(FoodDietary foodDietary);
}
