package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.request.food.UpdateFoodRequest;
import com.mthien.yumble.payload.response.food.FoodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    @Mapping(target = "image", ignore = true)
    Food createFood(CreateFoodRequest request);

    @Mapping(target = "image", ignore = true)
    void updateFood(@MappingTarget Food food, UpdateFoodRequest request);

    FoodResponse toFoodResponse(Food food);

    @Mapping(target = "image", expression = "java(image)")
    FoodResponse toFoodResponse(Food food, String image);

    List<FoodResponse> toFoodResponseList(List<Food> foods);
}
