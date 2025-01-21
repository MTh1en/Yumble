package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Nutrition;
import com.mthien.yumble.payload.request.nutrition.CreateNutritionRequest;
import com.mthien.yumble.payload.request.nutrition.UpdateNutritionRequest;
import com.mthien.yumble.payload.response.nutrition.NutritionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NutritionMapper {
    Nutrition createNutrition(CreateNutritionRequest request);

    void updateNutrition(@MappingTarget Nutrition nutrition, UpdateNutritionRequest request);

    NutritionResponse toNutritionResponse(Nutrition nutrition);

    List<NutritionResponse> toListNutritionResponse(List<Nutrition> nutritionList);
}
