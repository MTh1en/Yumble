package com.mthien.yumble.service;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodAllergy;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.FoodAllergyMapper;
import com.mthien.yumble.payload.request.food.allergy.AddAllergiesRequest;
import com.mthien.yumble.payload.response.food.allergy.FoodAllergyResponse;
import com.mthien.yumble.repository.AllergyRepo;
import com.mthien.yumble.repository.FoodAllergyRepo;
import com.mthien.yumble.repository.FoodRepo;
import org.springframework.stereotype.Service;

@Service
public class FoodAllergyService {
    private final FoodAllergyRepo foodAllergyRepo;
    private final FoodAllergyMapper foodAllergyMapper;
    private final FoodRepo foodRepo;
    private final AllergyRepo allergyRepo;

    public FoodAllergyService(FoodAllergyRepo foodAllergyRepo, FoodAllergyMapper foodAllergyMapper, FoodRepo foodRepo, AllergyRepo allergyRepo) {
        this.foodAllergyRepo = foodAllergyRepo;
        this.foodAllergyMapper = foodAllergyMapper;
        this.foodRepo = foodRepo;
        this.allergyRepo = allergyRepo;
    }

    public FoodAllergyResponse addAllergy(String foodId, AddAllergiesRequest request) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Allergy allergy = allergyRepo.findById(request.getAllergyId())
                .orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        foodAllergyRepo.findByAllergyAndFood(allergy, food).ifPresent(existingAllergy -> {
            throw new AppException(ErrorCode.FOOD_ALLERGY_IS_EXISTED);
        });
        FoodAllergy foodAllergy = foodAllergyMapper.createFoodAllergy(food, allergy, request.getSeverity());
        return foodAllergyMapper.toFoodAllergyResponse(foodAllergyRepo.save(foodAllergy));
    }

    public void deleteAllergy(String foodId, String allergyId) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Allergy allergy = allergyRepo.findById(allergyId).orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        FoodAllergy foodAllergy = foodAllergyRepo.findByAllergyAndFood(allergy, food)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_ALLERGY_NOT_FOUND));
        foodAllergyRepo.delete(foodAllergy);
    }

}
