package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.dietary.AddDietariesRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.dietary.FoodDietaryResponse;
import com.mthien.yumble.service.FoodDietaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodDietaryController {
    private final FoodDietaryService foodDietaryService;

    public FoodDietaryController(FoodDietaryService foodDietaryService) {
        this.foodDietaryService = foodDietaryService;
    }

    @PostMapping("foods/{foodId}/dietaries")
    public ResponseEntity<ApiResponse<FoodDietaryResponse>> addFoodDietary(@PathVariable("foodId") String foodId,
                                                                           @RequestBody AddDietariesRequest request) {
        var data = foodDietaryService.addDietary(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodDietaryResponse>builder()
                .code(200)
                .message("Chế độ ăn đã được thêm vào món ăn")
                .data(data)
                .build());
    }

    @DeleteMapping("foods/{foodId}/dietaries/{dietaryId}")
    public ResponseEntity<ApiResponse<Void>> deleteDietary(@PathVariable("foodId") String foodId,
                                                             @PathVariable("dietaryId") String dietaryId) {
        foodDietaryService.deleteDietary(foodId, dietaryId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa thành chế độ ăn khỏi món ăn thành công")
                .build());
    }
}
