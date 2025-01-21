package com.mthien.yumble.service;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.FoodMapper;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.request.food.UpdateFoodRequest;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.FoodRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepo foodRepo;
    private final FoodMapper foodMapper;

    public FoodService(FoodRepo foodRepo, FoodMapper foodMapper) {
        this.foodRepo = foodRepo;
        this.foodMapper = foodMapper;
    }

    public FoodResponse create(CreateFoodRequest request) {
        Food food = foodMapper.createFood(request);
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }

    public FoodResponse update(String id, UpdateFoodRequest request) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        foodMapper.updateFood(food, request);
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }

    public FoodResponse viewOne(String id) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        return foodMapper.toFoodResponse(food);
    }

    public List<FoodResponse> viewAll() {
        List<Food> foodList = foodRepo.findAll();
        return foodMapper.toFoodResponseList(foodList);
    }

    public void delete(String id) {
        foodRepo.deleteById(id);
    }
}
