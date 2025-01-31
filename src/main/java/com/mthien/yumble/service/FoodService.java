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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private final FoodRepo foodRepo;
    private final FoodMapper foodMapper;
    private final FirebaseService firebaseService;

    public FoodService(FoodRepo foodRepo, FoodMapper foodMapper, FirebaseService firebaseService) {
        this.foodRepo = foodRepo;
        this.foodMapper = foodMapper;
        this.firebaseService = firebaseService;
    }

    public FoodResponse create(CreateFoodRequest request) {
        Food food = foodMapper.createFood(request);
        food.setImage(firebaseService.uploadFile(generateUniqueFoodImageUrl(food), request.getImage()));
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }

    public FoodResponse update(String foodId, UpdateFoodRequest request) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        foodMapper.updateFood(food, request);
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }

    public FoodResponse viewOne(String foodId) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        String image = Optional.ofNullable(food.getImage()).filter(imageUrl -> imageUrl.contains("food")).map(firebaseService::getImageUrl).orElse(food.getImage());
        return foodMapper.toFoodResponse(food, image);
    }

    public List<FoodResponse> viewAll() {
        return foodRepo.findAll().stream().map(food1 -> {
            String image = Optional.ofNullable(food1.getImage()).filter(imageUrl -> imageUrl.contains("food")).map(firebaseService::getImageUrl).orElse(food1.getImage());
            return foodMapper.toFoodResponse(food1, image);
        }).collect(Collectors.toList());
    }

    public void delete(String foodId) {
        foodRepo.deleteById(foodId);
    }

    public FoodResponse uploadFoodImage(String foodId, MultipartFile file) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        food.setImage(firebaseService.uploadFile(generateUniqueFoodImageUrl(food), file));
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }

    public String generateUniqueFoodImageUrl(Food food) {
        String uniqueId = UUID.randomUUID().toString();
        return String.format("food/%s_%s", uniqueId, food.getName());
    }
}
