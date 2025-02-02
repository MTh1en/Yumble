package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.allergy.AddFoodAllergyRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.payload.response.food.allergy.FoodAllergyDetailResponse;
import com.mthien.yumble.payload.response.food.allergy.FoodAllergyResponse;
import com.mthien.yumble.service.FoodAllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods")
@RequiredArgsConstructor
public class FoodAllergyController {
    private final FoodAllergyService foodAllergyService;

    @PostMapping("/{foodId}/allergies")
    public ApiResponse<FoodAllergyDetailResponse> addAllergies(@PathVariable String foodId,
                                                               @RequestBody AddFoodAllergyRequest request) {
        return ApiResponse.<FoodAllergyDetailResponse>builder()
                .message("Thêm thành phần dị ứng thành công")
                .data(foodAllergyService.addFoodAllergy(foodId, request))
                .build();
    }

    @DeleteMapping("/{foodId}/allergies/{allergyId}")
    public ApiResponse<Void> deleteAllergies(@PathVariable("foodId") String foodId,
                                             @PathVariable("allergyId") String allergyId) {
        foodAllergyService.deleteFoodAllergy(foodId, allergyId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa thành phần dị ứng khỏi món ăn thành công")
                .build();
    }

    @GetMapping("/{foodId}/allergies")
    public ApiResponse<List<FoodAllergyResponse>> viewAllergiesByFood(@PathVariable("foodId") String foodId) {
        return ApiResponse.<List<FoodAllergyResponse>>builder()
                .message("Thông tin dị ứng của món ăn")
                .data(foodAllergyService.viewAllergiesByFood(foodId))
                .build();
    }
}
