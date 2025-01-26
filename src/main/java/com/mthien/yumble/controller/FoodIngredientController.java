package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.ingredient.AddIngredientRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.ingredient.FoodIngredientResponse;
import com.mthien.yumble.service.FoodIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodIngredientController {
    private final FoodIngredientService foodIngredientService;

    public FoodIngredientController(FoodIngredientService foodIngredientService) {
        this.foodIngredientService = foodIngredientService;
    }

    @PostMapping("foods/{foodId}/ingredients")
    public ResponseEntity<ApiResponse<FoodIngredientResponse>> addIngredient(@PathVariable("foodId") String foodId,
                                                                             @RequestBody AddIngredientRequest request) {
        var data = foodIngredientService.addIngredient(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodIngredientResponse>builder()
                .code(200)
                .message("Nguyên liệu đã được thêm vào món ăn")
                .data(data)
                .build());
    }

    @DeleteMapping("foods/{foodId}/ingredients/{ingredientId}")
    public ResponseEntity<ApiResponse<Void>> deleteIngredient(@PathVariable("foodId") String foodId,
                                                              @PathVariable("ingredientId") String ingredientId) {
        foodIngredientService.deleteIngredient(foodId, ingredientId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa nguyên liệu khỏi món ăn thành công")
                .build());
    }
}
