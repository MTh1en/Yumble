package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Ingredient;
import com.mthien.yumble.entity.FoodIngredient;
import com.mthien.yumble.payload.request.food.ingredient.AddIngredientRequest;
import com.mthien.yumble.payload.response.food.ingredient.FoodIngredientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodIngredientMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "food", expression = "java(food)")
    @Mapping(target = "ingredient", expression = "java(ingredient)")
    FoodIngredient addIngredient(Food food, Ingredient ingredient, AddIngredientRequest request);

    FoodIngredientResponse toIngredientResponse(FoodIngredient foodIngredient);
}
