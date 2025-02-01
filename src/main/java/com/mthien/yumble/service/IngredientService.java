package com.mthien.yumble.service;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Ingredient;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.IngredientMapper;
import com.mthien.yumble.payload.request.ingredient.CreateIngredientRequest;
import com.mthien.yumble.payload.request.ingredient.UpdateIngredientRequest;
import com.mthien.yumble.payload.response.ingredient.FoodIngredientResponse;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.repository.IngredientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final FoodRepo foodRepo;
    private final IngredientRepo ingredientRepo;
    private final IngredientMapper ingredientMapper;

    public IngredientResponse addIngredientIntoFood(String foodId, CreateIngredientRequest request) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Ingredient ingredient = ingredientMapper.createIngredient(food, request);
        return ingredientMapper.toIngredientResponse(ingredientRepo.save(ingredient));
    }

    public IngredientResponse updateIngredient(String ingredientId, UpdateIngredientRequest request) {
        Ingredient ingredient = ingredientRepo.findById(ingredientId).orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_FOUND));
        ingredientMapper.updateIngredient(ingredient, request);
        return ingredientMapper.toIngredientResponse(ingredientRepo.save(ingredient));
    }

    public void deleteIngredient(String ingredientId) {
        ingredientRepo.deleteById(ingredientId);
    }

    public List<FoodIngredientResponse> viewIngredientsByFood(String foodId) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        return ingredientRepo.findByFoodOrderByIsCoreDesc(food).stream()
                .map(ingredientMapper::toFoodIngredientResponse).collect(Collectors.toList());
    }
}
