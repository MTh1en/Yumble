package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Ingredient;
import com.mthien.yumble.payload.request.ingredient.CreateIngredientRequest;
import com.mthien.yumble.payload.request.ingredient.UpdateIngredientRequest;
import com.mthien.yumble.payload.response.ingredient.FoodIngredientResponse;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    @Mapping(target = "food", expression = "java(food)")
    Ingredient createIngredient(Food food, CreateIngredientRequest request);

    void updateIngredient(@MappingTarget Ingredient ingredient, UpdateIngredientRequest request);

    IngredientResponse toIngredientResponse(Ingredient ingredient);

    FoodIngredientResponse toFoodIngredientResponse(Ingredient ingredient);
}
