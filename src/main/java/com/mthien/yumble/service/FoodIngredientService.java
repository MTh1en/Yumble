package com.mthien.yumble.service;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Ingredient;
import com.mthien.yumble.entity.FoodIngredient;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.FoodIngredientMapper;
import com.mthien.yumble.payload.request.food.ingredient.AddIngredientRequest;
import com.mthien.yumble.payload.response.food.ingredient.FoodIngredientResponse;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.repository.IngredientRepo;
import com.mthien.yumble.repository.FoodIngredientRepo;
import org.springframework.stereotype.Service;

@Service
public class FoodIngredientService {
    private final FoodIngredientMapper foodIngredientMapper;
    private final FoodIngredientRepo foodIngredientRepo;
    private final FoodRepo foodRepo;
    private final IngredientRepo ingredientRepo;

    public FoodIngredientService(FoodIngredientMapper foodIngredientMapper, FoodIngredientRepo foodIngredientRepo, FoodRepo foodRepo, IngredientRepo ingredientRepo) {
        this.foodIngredientMapper = foodIngredientMapper;
        this.foodIngredientRepo = foodIngredientRepo;
        this.foodRepo = foodRepo;
        this.ingredientRepo = ingredientRepo;
    }

    public FoodIngredientResponse addFoodIngredient(String foodId, AddIngredientRequest request) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Ingredient ingredient = ingredientRepo.findById(request.getIngredientId())
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_FOUND));
        foodIngredientRepo.findByFoodAndIngredient(food, ingredient).ifPresent(existingIngredient -> {
            throw new AppException(ErrorCode.INGREDIENT_USING_IS_EXITED);
        });
        FoodIngredient foodIngredient = foodIngredientMapper.addIngredient(food, ingredient, request);
        return foodIngredientMapper.toIngredientResponse(foodIngredientRepo.save(foodIngredient));
    }

    public void deleteFoodIngredient(String foodId, String ingredientId) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Ingredient ingredient = ingredientRepo.findById(ingredientId)
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_FOUND));
        FoodIngredient foodIngredient = foodIngredientRepo.findByFoodAndIngredient(food, ingredient)
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_USING_NOT_FOUND));
        foodIngredientRepo.delete(foodIngredient);
    }
}
