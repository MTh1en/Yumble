package com.mthien.yumble.service;

import com.mthien.yumble.entity.*;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.*;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.request.food.UpdateFoodRequest;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final FirebaseService firebaseService;

    public FoodService(FoodMapper foodMapper, FoodRepo foodRepo, AllergyRepo allergyRepo, DietaryRepo dietaryRepo, MethodCookingRepo methodCookingRepo, StepRepo stepRepo, FirebaseService firebaseService) {
        this.foodMapper = foodMapper;
        this.foodRepo = foodRepo;
        this.allergyRepo = allergyRepo;
        this.dietaryRepo = dietaryRepo;
        this.methodCookingRepo = methodCookingRepo;
        this.stepRepo = stepRepo;
        this.firebaseService = firebaseService;
    }

    public FoodResponse create(CreateFoodRequest request) throws IOException {
        Food food = foodMapper.createFood(request);
        food.setImage(firebaseService.uploadFile(request.getName(), request.getImage()));
        food.setAllergies(mapToEntities(request.getAllergies(), allergyRepo::findAllByNameIn, "allergies"));
        food.setDietaries(mapToEntities(request.getDietaries(), dietaryRepo::findAllByNameIn, "dietaries"));
        food.setMethodCooking(mapToEntities(request.getMethodCooking(), methodCookingRepo::findAllByNameIn, "methodCookings"));
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }

    public FoodResponse update(String id, UpdateFoodRequest request) throws IOException {
        Food founded = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Food foodUpdated = foodMapper.toFood(founded);
        foodMapper.updateFood(foodUpdated, request);
        foodRepo.save(foodUpdated);
        if (!request.getImage().isEmpty()) {
            foodUpdated.setImage(firebaseService.uploadFile(request.getName(), request.getImage()));
        }
        foodUpdated.setAllergies(mapToEntities(request.getAllergies(), allergyRepo::findAllByNameIn, "allergies"));
        foodUpdated.setDietaries(mapToEntities(request.getDietaries(), dietaryRepo::findAllByNameIn, "dietaries"));
        foodUpdated.setMethodCooking(mapToEntities(request.getMethodCooking(), methodCookingRepo::findAllByNameIn, "methodCookings"));
        foodRepo.save(foodUpdated);
        foodUpdated.setSteps(stepRepo.findByFoodIdOrderByStepOrder(id));
        return foodMapper.toFoodResponse(foodUpdated);
    }

    public FoodResponse viewOne(String id) {
        Food foodFounded = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Food food = foodMapper.toFood(foodFounded);
        food.setAllergies(foodRepo.findAllergiesByFoodId(id));
        food.setDietaries(foodRepo.findDietaryByFoodId(id));
        food.setMethodCooking(foodRepo.findMethodCookingByFoodId(id));
        food.setSteps(stepRepo.findByFoodIdOrderByStepOrder(id));
        return foodMapper.toFoodResponse(food);
    }

    public Set<FoodResponse> viewAll() {
        Set<Food> foodSet = foodRepo.findAllFood();
        return foodSet.stream().map(food -> {
            Food newFood = foodMapper.toFood(food);
            newFood.setAllergies(foodRepo.findAllergiesByFoodId(food.getId()));
            newFood.setDietaries(foodRepo.findDietaryByFoodId(food.getId()));
            newFood.setMethodCooking(foodRepo.findMethodCookingByFoodId(food.getId()));
            newFood.setSteps(stepRepo.findByFoodIdOrderByStepOrder(food.getId()));
            return foodMapper.toFoodResponse(newFood);
        }).collect(Collectors.toSet());
    }

    public void delete(String id) {
        Food foodFounded = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Set<Step> foodStep = stepRepo.findByFoodIdOrderByStepOrder(id);
        stepRepo.deleteAll(foodStep);
        foodRepo.delete(foodFounded);
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
