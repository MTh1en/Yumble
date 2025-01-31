package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.dietary.AddFoodDietaryRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryDetailResponse;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryResponse;
import com.mthien.yumble.service.FoodDietaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods")
public class FoodDietaryController {
    private final FoodDietaryService foodDietaryService;

    public FoodDietaryController(FoodDietaryService foodDietaryService) {
        this.foodDietaryService = foodDietaryService;
    }

    @PostMapping("/{foodId}/dietaries")
    public ResponseEntity<ApiResponse<FoodDietaryDetailResponse>> addFoodDietary(@PathVariable("foodId") String foodId,
                                                                                 @RequestBody AddFoodDietaryRequest request) {
        var data = foodDietaryService.addFoodDietary(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodDietaryDetailResponse>builder()
                .code(200)
                .message("Chế độ ăn đã được thêm vào món ăn")
                .data(data)
                .build());
    }

    @DeleteMapping("/{foodId}/dietaries/{dietaryId}")
    public ResponseEntity<ApiResponse<Void>> deleteDietary(@PathVariable("foodId") String foodId,
                                                           @PathVariable("dietaryId") String dietaryId) {
        foodDietaryService.deleteFoodDietary(foodId, dietaryId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa thành chế độ ăn khỏi món ăn thành công")
                .build());
    }

    @GetMapping("/{foodId}/dietaries")
    public ResponseEntity<ApiResponse<List<FoodDietaryResponse>>> viewDietariesByFood(@PathVariable("foodId") String foodId) {
        var data = foodDietaryService.viewDietariesByFood(foodId);
        return ResponseEntity.ok(ApiResponse.<List<FoodDietaryResponse>>builder()
                .code(200)
                .message("Thông tin chế độ ăn của món ăn")
                .data(data)
                .build());
    }
}
