package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.allergy.AddFoodAllergyRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.allergy.FoodAllergyDetailResponse;
import com.mthien.yumble.payload.response.food.allergy.FoodAllergyResponse;
import com.mthien.yumble.service.FoodAllergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods")
public class FoodAllergyController {
    private final FoodAllergyService foodAllergyService;

    public FoodAllergyController(FoodAllergyService foodAllergyService) {
        this.foodAllergyService = foodAllergyService;
    }

    @PostMapping("/{foodId}/allergies")
    public ResponseEntity<ApiResponse<FoodAllergyDetailResponse>> addAllergies(@PathVariable String foodId,
                                                                               @RequestBody AddFoodAllergyRequest request) {
        var data = foodAllergyService.addFoodAllergy(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodAllergyDetailResponse>builder()
                .code(200)
                .message("Thêm thành phần dị ứng thành công")
                .data(data)
                .build());
    }

    @DeleteMapping("/{foodId}/allergies/{allergyId}")
    public ResponseEntity<ApiResponse<Void>> deleteAllergies(@PathVariable("foodId") String foodId,
                                                             @PathVariable("allergyId") String allergyId) {
        foodAllergyService.deleteFoodAllergy(foodId, allergyId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa thành phần dị ứng khỏi món ăn thành công")
                .build());
    }

    @GetMapping("/{foodId}/allergies")
    public ResponseEntity<ApiResponse<List<FoodAllergyResponse>>> viewAllergiesByFood(@PathVariable("foodId") String foodId) {
        var data = foodAllergyService.viewAllergiesByFood(foodId);
        return ResponseEntity.ok(ApiResponse.<List<FoodAllergyResponse>>builder()
                .code(200)
                .message("Thông tin dị ứng của món ăn")
                .data(data)
                .build());
    }
}
