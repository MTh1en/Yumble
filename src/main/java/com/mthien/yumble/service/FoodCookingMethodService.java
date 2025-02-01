package com.mthien.yumble.service;

import com.mthien.yumble.entity.CookingMethod;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodCookingMethod;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.CookingMethodMapper;
import com.mthien.yumble.mapper.FoodCookingMethodMapper;
import com.mthien.yumble.payload.request.food.cookingmethod.AddFoodCookingMethodRequest;
import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodDetailResponse;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodResponse;
import com.mthien.yumble.repository.CookingMethodRepo;
import com.mthien.yumble.repository.FoodCookingMethodRepo;
import com.mthien.yumble.repository.FoodRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodCookingMethodService {
    private final FoodCookingMethodRepo foodCookingMethodRepo;
    private final FoodCookingMethodMapper foodCookingMethodMapper;
    private final FoodRepo foodRepo;
    private final CookingMethodRepo cookingMethodRepo;

    public FoodCookingMethodDetailResponse addFoodCookingMethod(String foodId, AddFoodCookingMethodRequest request) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        CookingMethod cookingMethod = cookingMethodRepo.findById(request.getCookingMethodId())
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        foodCookingMethodRepo.findByFoodAndCookingMethod(food, cookingMethod).ifPresent(existedCookingMethod -> {
            throw new AppException(ErrorCode.FOOD_COOKING_METHOD_EXISTED);
        });
        FoodCookingMethod foodCookingMethod = foodCookingMethodMapper.createFoodCookingMethod(food, cookingMethod, request);
        return foodCookingMethodMapper.toFoodCookingMethodDetailResponse(foodCookingMethodRepo.save(foodCookingMethod));
    }

    public void deleteFoodCookingMethod(String foodId, String cookingMethodId) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        CookingMethod cookingMethod = cookingMethodRepo.findById(cookingMethodId)
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        FoodCookingMethod foodCookingMethod = foodCookingMethodRepo.findByFoodAndCookingMethod(food, cookingMethod)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_COOKING_METHOD_EXISTED));
        foodCookingMethodRepo.delete(foodCookingMethod);
    }

    public List<FoodCookingMethodResponse> viewCookingMethodsByFood(String foodId) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        return foodCookingMethodRepo.findByFood(food).stream().map(foodCookingMethodMapper::toFoodCookingMethodResponse).collect(Collectors.toList());
    }
}
