package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.ingredient.AddIngredientRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.ingredient.FoodIngredientDetailResponse;
import com.mthien.yumble.payload.response.food.ingredient.FoodIngredientResponse;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import com.mthien.yumble.service.FoodIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods/")
public class FoodIngredientController {
    private final FoodIngredientService foodIngredientService;

    public FoodIngredientController(FoodIngredientService foodIngredientService) {
        this.foodIngredientService = foodIngredientService;
    }

    @PostMapping("/{foodId}/ingredients")
    public ResponseEntity<ApiResponse<FoodIngredientDetailResponse>> addIngredient(@PathVariable("foodId") String foodId,
                                                                                   @RequestBody AddIngredientRequest request) {
        var data = foodIngredientService.addFoodIngredient(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodIngredientDetailResponse>builder()
                .code(200)
                .message("Nguyên liệu đã được thêm vào món ăn")
                .data(data)
                .build());
    }

    @DeleteMapping("/{foodId}/ingredients/{ingredientId}")
    public ResponseEntity<ApiResponse<Void>> deleteIngredient(@PathVariable("foodId") String foodId,
                                                              @PathVariable("ingredientId") String ingredientId) {
        foodIngredientService.deleteFoodIngredient(foodId, ingredientId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa nguyên liệu khỏi món ăn thành công")
                .build());
    }

    @GetMapping("/{foodId}/ingredients")
    public ResponseEntity<ApiResponse<List<FoodIngredientResponse>>> viewIngredientsByFood(@PathVariable("foodId") String foodId) {
        var data = foodIngredientService.viewIngredientByFood(foodId);
        return ResponseEntity.ok(ApiResponse.<List<FoodIngredientResponse>>builder()
                .code(200)
                .message("Nguyên liệu được sử dụng trong món ăn")
                .data(data)
                .build());
    }
}
