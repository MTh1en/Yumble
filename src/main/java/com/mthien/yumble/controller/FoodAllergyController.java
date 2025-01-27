package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.allergy.AddFoodAllergyRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.allergy.FoodAllergyResponse;
import com.mthien.yumble.service.FoodAllergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodAllergyController {
    private final FoodAllergyService foodAllergyService;

    public FoodAllergyController(FoodAllergyService foodAllergyService) {
        this.foodAllergyService = foodAllergyService;
    }

    @PostMapping("foods/{foodId}/allergies")
    public ResponseEntity<ApiResponse<FoodAllergyResponse>> addAllergies(@PathVariable String foodId,
                                                                         @RequestBody AddFoodAllergyRequest request) {
        var data = foodAllergyService.addFoodAllergy(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodAllergyResponse>builder()
                .code(200)
                .message("Thêm thành phần dị ứng thành công")
                .data(data)
                .build());
    }

    @DeleteMapping("foods/{foodId}/allergies/{allergyId}")
    public ResponseEntity<ApiResponse<Void>> deleteAllergies(@PathVariable("foodId") String foodId,
                                                             @PathVariable("allergyId") String allergyId) {
        foodAllergyService.deleteFoodAllergy(foodId, allergyId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa thành phần dị ứng khỏi món ăn thành công")
                .build());
    }
}
