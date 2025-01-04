package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.*;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.response.food.FoodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Mapper(componentModel = "spring")
public interface FoodMapper {
    @Mapping(target = "allergies", ignore = true)
    @Mapping(target = "dietaries", ignore = true)
    @Mapping(target = "methodCooking", ignore = true)
    @Mapping(target = "steps", ignore = true)
    @Mapping(target = "image", ignore = true)
    Food createFood(CreateFoodRequest request);

    @Mapping(target = "allergies", source = "allergies")
    @Mapping(target = "dietaries", source = "dietaries")
    @Mapping(target = "methodCooking", source = "methodCooking")
    @Mapping(target = "steps", source = "steps")
    FoodResponse toFoodResponse(Food food);

    @Mapping(target = "allergies", ignore = true)
    @Mapping(target = "dietaries", ignore = true)
    @Mapping(target = "methodCooking", ignore = true)
    @Mapping(target = "steps", ignore = true)
    Food toFood(Food Food);
}
