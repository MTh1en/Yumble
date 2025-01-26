package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.CookingMethod;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodCookingMethod;
import com.mthien.yumble.payload.request.food.cookingmethod.AddCookingMethodRequest;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodCookingMethodMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "food", expression = "java(food)")
    @Mapping(target = "cookingMethod", expression = "java(cookingMethod)")
    FoodCookingMethod createFoodCookingMethod(Food food, CookingMethod cookingMethod, AddCookingMethodRequest request);

    FoodCookingMethodResponse toFoodCookingMethodResponse(FoodCookingMethod foodCookingMethod);
}
