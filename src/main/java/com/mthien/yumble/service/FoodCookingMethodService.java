package com.mthien.yumble.service;

import com.mthien.yumble.entity.CookingMethod;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodCookingMethod;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.FoodCookingMethodMapper;
import com.mthien.yumble.payload.request.food.cookingmethod.AddCookingMethodRequest;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodResponse;
import com.mthien.yumble.repository.CookingMethodRepo;
import com.mthien.yumble.repository.FoodCookingMethodRepo;
import com.mthien.yumble.repository.FoodRepo;
import org.springframework.stereotype.Service;

@Service
public class FoodCookingMethodService {
    private final FoodCookingMethodRepo foodCookingMethodRepo;
    private final FoodCookingMethodMapper foodCookingMethodMapper;
    private final FoodRepo foodRepo;
    private final CookingMethodRepo cookingMethodRepo;

    public FoodCookingMethodService(FoodCookingMethodRepo foodCookingMethodRepo, FoodCookingMethodMapper foodCookingMethodMapper, FoodRepo foodRepo, CookingMethodRepo cookingMethodRepo) {
        this.foodCookingMethodRepo = foodCookingMethodRepo;
        this.foodCookingMethodMapper = foodCookingMethodMapper;
        this.foodRepo = foodRepo;
        this.cookingMethodRepo = cookingMethodRepo;
    }

    public FoodCookingMethodResponse addCookingMethod(String foodId, AddCookingMethodRequest request) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        CookingMethod cookingMethod = cookingMethodRepo.findById(request.getCookingMethodId())
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        foodCookingMethodRepo.findByFoodAndCookingMethod(food, cookingMethod).ifPresent(existedCookingMethod -> {
            throw new AppException(ErrorCode.FOOD_COOKING_METHOD_EXISTED);
        });
        FoodCookingMethod foodCookingMethod = foodCookingMethodMapper.createFoodCookingMethod(food, cookingMethod, request);
        return foodCookingMethodMapper.toFoodCookingMethodResponse(foodCookingMethodRepo.save(foodCookingMethod));
    }

    public void deleteCookingMethod(String foodId, String cookingMethodId) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        CookingMethod cookingMethod = cookingMethodRepo.findById(cookingMethodId)
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        FoodCookingMethod foodCookingMethod = foodCookingMethodRepo.findByFoodAndCookingMethod(food, cookingMethod)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_COOKING_METHOD_EXISTED));
        foodCookingMethodRepo.delete(foodCookingMethod);
    }
}
