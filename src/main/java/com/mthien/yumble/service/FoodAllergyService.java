package com.mthien.yumble.service;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodAllergy;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.AllergyMapper;
import com.mthien.yumble.mapper.FoodAllergyMapper;
import com.mthien.yumble.payload.request.food.allergy.AddFoodAllergyRequest;
import com.mthien.yumble.payload.response.food.allergy.FoodAllergyDetailResponse;
import com.mthien.yumble.payload.response.food.allergy.FoodAllergyResponse;
import com.mthien.yumble.repository.AllergyRepo;
import com.mthien.yumble.repository.FoodAllergyRepo;
import com.mthien.yumble.repository.FoodRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodAllergyService {
    private final FoodAllergyRepo foodAllergyRepo;
    private final FoodAllergyMapper foodAllergyMapper;
    private final FoodRepo foodRepo;
    private final AllergyRepo allergyRepo;

    public FoodAllergyDetailResponse addFoodAllergy(String foodId, AddFoodAllergyRequest request) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Allergy allergy = allergyRepo.findById(request.getAllergyId())
                .orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        foodAllergyRepo.findByAllergyAndFood(allergy, food).ifPresent(existingAllergy -> {
            throw new AppException(ErrorCode.FOOD_ALLERGY_IS_EXISTED);
        });
        FoodAllergy foodAllergy = foodAllergyMapper.createFoodAllergy(food, allergy, request.getSeverity());
        return foodAllergyMapper.toFoodAllergyDetailResponse(foodAllergyRepo.save(foodAllergy));
    }

    public void deleteFoodAllergy(String foodId, String allergyId) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Allergy allergy = allergyRepo.findById(allergyId)
                .orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        FoodAllergy foodAllergy = foodAllergyRepo.findByAllergyAndFood(allergy, food)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_ALLERGY_NOT_FOUND));
        foodAllergyRepo.delete(foodAllergy);
    }

    public List<FoodAllergyResponse> viewAllergiesByFood(String foodId) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        return foodAllergyRepo.findByFood(food).stream().map(foodAllergyMapper::toFoodAllergyResponse).collect(Collectors.toList());
    }
}
