package com.mthien.yumble.service;

import com.mthien.yumble.entity.*;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.FoodMapper;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private final FoodMapper foodMapper;
    private final FoodRepo foodRepo;
    private final AllergyRepo allergyRepo;
    private final DietaryRepo dietaryRepo;
    private final MethodCookingRepo methodCookingRepo;
    private final StepRepo stepRepo;

    public FoodService(FoodMapper foodMapper, FoodRepo foodRepo, AllergyRepo allergyRepo, DietaryRepo dietaryRepo, MethodCookingRepo methodCookingRepo, StepRepo stepRepo) {
        this.foodMapper = foodMapper;
        this.foodRepo = foodRepo;
        this.allergyRepo = allergyRepo;
        this.dietaryRepo = dietaryRepo;
        this.methodCookingRepo = methodCookingRepo;
        this.stepRepo = stepRepo;
    }

    public FoodResponse create(CreateFoodRequest request) {
        Food food = foodMapper.createFood(request);
        food.setAllergies(mapToEntities(request.getAllergies(), allergyRepo::findAllByNameIn, "allergies"));
        food.setDietaries(mapToEntities(request.getDietaries(), dietaryRepo::findAllByNameIn, "dietaries"));
        food.setMethodCooking(mapToEntities(request.getMethodCooking(), methodCookingRepo::findAllByNameIn, "methodCookings"));
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }

    public FoodResponse viewOne(String id) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Food foodResponse = foodMapper.toFood(food);
        foodResponse.setAllergies(foodRepo.findAllergiesByFoodId(id));
        foodResponse.setDietaries(foodRepo.findDietaryByFoodId(id));
        foodResponse.setMethodCooking(foodRepo.findMethodCookingByFoodId(id));
        foodResponse.setSteps(stepRepo.findByFoodIdOrderByStepOrder(id));
        return foodMapper.toFoodResponse(foodResponse);
    }


    private <T> Set<T> mapToEntities(Set<String> input, Function<Set<String>, Set<T>> processFunction, String typeName) {
        Set<T> foundEntities = processFunction.apply(input);
        Set<String> notFound = input.stream()
                .filter(name -> foundEntities.stream()
                        .noneMatch(entity -> getName(entity).equalsIgnoreCase(name)))
                .collect(Collectors.toSet());

        if (!notFound.isEmpty()) {
            throw new RuntimeException("Không tìm thấy " + typeName + " với tên: " + String.join(", ", notFound));
        }
        return foundEntities;
    }

    private <T> String getName(T entity) {
        if (entity instanceof Allergy) {
            return ((Allergy) entity).getName();
        } else if (entity instanceof Dietary) {
            return ((Dietary) entity).getName();
        } else if (entity instanceof MethodCooking) {
            return ((MethodCooking) entity).getName();
        }
        return "";
    }
}
