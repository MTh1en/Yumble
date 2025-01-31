package com.mthien.yumble.service;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodDietary;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.FoodDietaryMapper;
import com.mthien.yumble.payload.request.food.dietary.AddFoodDietaryRequest;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryDetailResponse;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryResponse;
import com.mthien.yumble.repository.DietaryRepo;
import com.mthien.yumble.repository.FoodDietaryRepo;
import com.mthien.yumble.repository.FoodRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodDietaryService {
    private final FoodDietaryRepo foodDietaryRepo;
    private final FoodDietaryMapper foodDietaryMapper;
    private final FoodRepo foodRepo;
    private final DietaryRepo dietaryRepo;

    public FoodDietaryService(FoodDietaryRepo foodDietaryRepo, FoodDietaryMapper foodDietaryMapper, FoodRepo foodRepo, DietaryRepo dietaryRepo) {
        this.foodDietaryRepo = foodDietaryRepo;
        this.foodDietaryMapper = foodDietaryMapper;
        this.foodRepo = foodRepo;
        this.dietaryRepo = dietaryRepo;
    }

    public FoodDietaryDetailResponse addFoodDietary(String foodId, AddFoodDietaryRequest request) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Dietary dietary = dietaryRepo.findById(request.getDietaryId())
                .orElseThrow(() -> new AppException(ErrorCode.DIETARY_NOT_FOUND));
        foodDietaryRepo.findByFoodAndDietary(food, dietary).ifPresent(existed -> {
            throw new AppException(ErrorCode.FOOD_DIETARY_IS_EXISTED);
        });
        FoodDietary foodDietary = foodDietaryMapper.createFoodDietary(food, dietary, request.getPriority());
        return foodDietaryMapper.toFoodDietaryDetailResponse(foodDietaryRepo.save(foodDietary));
    }

    public void deleteFoodDietary(String foodId, String dietaryId) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Dietary dietary = dietaryRepo.findById(dietaryId)
                .orElseThrow(() -> new AppException(ErrorCode.DIETARY_NOT_FOUND));
        FoodDietary foodDietary = foodDietaryRepo.findByFoodAndDietary(food, dietary)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_DIETARY_NOT_FOUND));
        foodDietaryRepo.delete(foodDietary);
    }

    public List<FoodDietaryResponse> viewDietariesByFood(String foodId) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        return foodDietaryRepo.findByFood(food).stream().map(foodDietaryMapper::toFoodDietaryResponse).collect(Collectors.toList());
    }
}
