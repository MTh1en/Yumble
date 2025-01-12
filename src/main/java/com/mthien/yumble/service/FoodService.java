package com.mthien.yumble.service;

import com.mthien.yumble.entity.*;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.*;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.request.food.UpdateFoodRequest;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.*;
import com.mthien.yumble.utils.RelationshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private static final Logger log = LoggerFactory.getLogger(FoodService.class);
    private final FoodMapper foodMapper;
    private final FoodRepo foodRepo;
    private final AllergyRepo allergyRepo;
    private final DietaryRepo dietaryRepo;
    private final MethodCookingRepo methodCookingRepo;
    private final StepRepo stepRepo;
    private final FirebaseService firebaseService;
    private final RelationshipService relationshipService;

    public FoodService(FoodMapper foodMapper, FoodRepo foodRepo, AllergyRepo allergyRepo, DietaryRepo dietaryRepo, MethodCookingRepo methodCookingRepo, StepRepo stepRepo, FirebaseService firebaseService, RelationshipService relationshipService) {
        this.foodMapper = foodMapper;
        this.foodRepo = foodRepo;
        this.allergyRepo = allergyRepo;
        this.dietaryRepo = dietaryRepo;
        this.methodCookingRepo = methodCookingRepo;
        this.stepRepo = stepRepo;
        this.firebaseService = firebaseService;
        this.relationshipService = relationshipService;
    }

    public FoodResponse create(CreateFoodRequest request) throws IOException {
        Food food = foodMapper.createFood(request);
        food.setImage(firebaseService.uploadFile(request.getName(), request.getImage()));
        food.setAllergies(relationshipService.convertSetStringToSetObject(request.getAllergies(), allergyRepo::findAllByNameIn, "allergies"));
        food.setDietaries(relationshipService.convertSetStringToSetObject(request.getDietaries(), dietaryRepo::findAllByNameIn, "dietaries"));
        food.setMethodCooking(relationshipService.convertSetStringToSetObject(request.getMethodCooking(), methodCookingRepo::findAllByNameIn, "methodCookings"));
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }

    public FoodResponse updateInformation(String id, UpdateFoodRequest request) throws IOException {
        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        foodMapper.updateFood(food, request);

        foodRepo.deleteAllergiesByFoodId(id);
        foodRepo.deleteDietaryByFoodId(id);
        foodRepo.deleteMethodCookingByFoodId(id);

        food.setAllergies(relationshipService.convertSetStringToSetObject(request.getAllergies(), allergyRepo::findAllByNameIn, "allergies"));
        food.setDietaries(relationshipService.convertSetStringToSetObject(request.getDietaries(), dietaryRepo::findAllByNameIn, "dietaries"));
        food.setMethodCooking(relationshipService.convertSetStringToSetObject(request.getMethodCooking(), methodCookingRepo::findAllByNameIn, "methodCookings"));
        foodRepo.save(food);
        food.setSteps(stepRepo.findByFoodIdOrderByStepOrder(id));
        return foodMapper.toFoodResponse(food);
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
}
