package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Ingredient;
import com.mthien.yumble.payload.request.ingredient.CreateIngredientRequest;
import com.mthien.yumble.payload.request.ingredient.UpdateIngredientRequest;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient createIngredient(CreateIngredientRequest request);

    void updateIngredient(@MappingTarget Ingredient ingredient, UpdateIngredientRequest request);

    IngredientResponse toIngredientResponse(Ingredient ingredient);

    List<IngredientResponse> toListIngredientResponses(List<Ingredient> ingredients);
}
