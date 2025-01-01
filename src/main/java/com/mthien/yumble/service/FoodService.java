package com.mthien.yumble.service;

import com.mthien.yumble.entity.*;
import com.mthien.yumble.mapper.FoodMapper;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.AllergyRepo;
import com.mthien.yumble.repository.DietaryRepo;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.repository.MethodCookingRepo;
import org.springframework.stereotype.Service;
import java.util.Arrays;
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

    public FoodService(FoodMapper foodMapper, FoodRepo foodRepo, AllergyRepo allergyRepo, DietaryRepo dietaryRepo, MethodCookingRepo methodCookingRepo) {
        this.foodMapper = foodMapper;
        this.foodRepo = foodRepo;
        this.allergyRepo = allergyRepo;
        this.dietaryRepo = dietaryRepo;
        this.methodCookingRepo = methodCookingRepo;
    }

    public FoodResponse create(CreateFoodRequest request) {
        Set<Allergy> allergies = mapToEntities(request.getAllergies(), allergyRepo::findAllByNameIn, "allergy");
        Set<Dietary> dietaries = mapToEntities(request.getDietaries(), dietaryRepo::findAllByNameIn, "dietaries");
        Set<MethodCooking> methodCookings = mapToEntities(request.getMethodCooking(), methodCookingRepo::findAllByNameIn, "methodCookings");

        Food food = foodMapper.createFood(request);
        food.setAllergies(allergies);
        food.setDietaries(dietaries);
        food.setMethodCooking(methodCookings);
        return foodMapper.toFoodResponse(foodRepo.save(food));
    }


    private <T> Set<T> mapToEntities(String input, Function<Set<String>, Set<T>> processFunction, String typeName) {
        if (input == null || input.isEmpty()) {
            return Set.of();
        }
        Set<String> names = Arrays.stream(input.trim().split(",")).map(String::trim).collect(Collectors.toSet());
        Set<T> foundEntities = processFunction.apply(names);
        Set<String> notFound = names.stream()
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
