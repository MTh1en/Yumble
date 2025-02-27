package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodDietary;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryDetailResponse;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodDietaryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "food", expression = "java(food)")
    @Mapping(target = "dietary", expression = "java(dietary)")
    FoodDietary createFoodDietary(Food food, Dietary dietary);

    FoodDietaryDetailResponse toFoodDietaryDetailResponse(FoodDietary foodDietary);

    FoodDietaryResponse toFoodDietaryResponse(FoodDietary foodDietary);
}
