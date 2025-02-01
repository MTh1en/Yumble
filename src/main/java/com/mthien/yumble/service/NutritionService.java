package com.mthien.yumble.service;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Nutrition;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.NutritionMapper;
import com.mthien.yumble.payload.request.nutrition.CreateNutritionRequest;
import com.mthien.yumble.payload.request.nutrition.UpdateNutritionRequest;
import com.mthien.yumble.payload.response.nutrition.NutritionResponse;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.repository.NutritionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NutritionService {
    private final NutritionRepo nutritionRepo;
    private final NutritionMapper nutritionMapper;
    private final FoodRepo foodRepo;

    public NutritionResponse create(String id, CreateNutritionRequest request) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Nutrition nutrition = nutritionMapper.createNutrition(request);
        nutrition.setFood(food);
        return nutritionMapper.toNutritionResponse(nutritionRepo.save(nutrition));
    }

    public NutritionResponse update(String id, UpdateNutritionRequest request) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Nutrition nutrition = nutritionRepo.findByFood(food).orElseThrow(() -> new AppException(ErrorCode.NUTRITION_NOT_FOUND));
        nutritionMapper.updateNutrition(nutrition, request);
        return nutritionMapper.toNutritionResponse(nutritionRepo.save(nutrition));
    }

    public NutritionResponse viewByFood(String id) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Nutrition nutrition = nutritionRepo.findByFood(food).orElseThrow(() -> new AppException(ErrorCode.NUTRITION_NOT_FOUND));
        return nutritionMapper.toNutritionResponse(nutrition);
    }

    public List<NutritionResponse> viewAll() {
        List<Nutrition> nutritionList = nutritionRepo.findAll();
        return nutritionMapper.toListNutritionResponse(nutritionList);
    }

    public void delete(String id) {
        nutritionRepo.deleteById(id);
    }
}
