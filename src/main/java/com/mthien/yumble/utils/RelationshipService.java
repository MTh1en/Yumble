package com.mthien.yumble.utils;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.MethodCooking;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.repository.FoodRepo;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RelationshipService {
    private final FoodRepo foodRepo;

    public RelationshipService(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    public <T> Set<T> convertSetStringToSetObject(
            Set<String> input,
            Function<Set<String>, Set<T>> processFunction,
            String typeName) {
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

    public void updateRelationships(
            String id,
            Set<?> relationships,
            BiConsumer<Food, Set<?>> setter,
            Consumer<String> deleteFunction) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        deleteFunction.accept(id);
        setter.accept(food, relationships);
    }


    //    public FoodResponse updateAllergies(String id, Set<Allergy> request) {
//        foodRepo.deleteAllergiesByFoodId(id);
//        Food food = foodRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
//        food.setAllergies(request);
//        return foodMapper.toFoodResponse(foodRepo.save(food));
//    }
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
