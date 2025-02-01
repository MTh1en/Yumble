package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.dietary.AddFoodDietaryRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryDetailResponse;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryResponse;
import com.mthien.yumble.service.FoodDietaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods")
@RequiredArgsConstructor
public class FoodDietaryController {
    private final FoodDietaryService foodDietaryService;

    @PostMapping("/{foodId}/dietaries")
    public ApiResponse<FoodDietaryDetailResponse> addFoodDietary(@PathVariable("foodId") String foodId,
                                                                 @RequestBody AddFoodDietaryRequest request) {
        return ApiResponse.<FoodDietaryDetailResponse>builder()
                .message("Chế độ ăn đã được thêm vào món ăn")
                .data(foodDietaryService.addFoodDietary(foodId, request))
                .build();
    }

    @DeleteMapping("/{foodId}/dietaries/{dietaryId}")
    public ApiResponse<Void> deleteDietary(@PathVariable("foodId") String foodId,
                                           @PathVariable("dietaryId") String dietaryId) {
        foodDietaryService.deleteFoodDietary(foodId, dietaryId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa thành chế độ ăn khỏi món ăn thành công")
                .build();
    }

    @GetMapping("/{foodId}/dietaries")
    public ApiResponse<List<FoodDietaryResponse>> viewDietariesByFood(@PathVariable("foodId") String foodId) {
        return ApiResponse.<List<FoodDietaryResponse>>builder()
                .message("Thông tin chế độ ăn của món ăn")
                .data(foodDietaryService.viewDietariesByFood(foodId))
                .build();
    }
}
