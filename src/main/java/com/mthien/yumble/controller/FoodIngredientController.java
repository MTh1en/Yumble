package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.ingredient.AddIngredientRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.ingredient.FoodIngredientDetailResponse;
import com.mthien.yumble.payload.response.food.ingredient.FoodIngredientResponse;
import com.mthien.yumble.service.FoodIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods/")
@RequiredArgsConstructor
public class FoodIngredientController {
    private final FoodIngredientService foodIngredientService;

    @PostMapping("/{foodId}/ingredients")
    public ApiResponse<FoodIngredientDetailResponse> addIngredient(@PathVariable("foodId") String foodId,
                                                                   @RequestBody AddIngredientRequest request) {
        return ApiResponse.<FoodIngredientDetailResponse>builder()
                .message("Nguyên liệu đã được thêm vào món ăn")
                .data(foodIngredientService.addFoodIngredient(foodId, request))
                .build();
    }

    @DeleteMapping("/{foodId}/ingredients/{ingredientId}")
    public ApiResponse<Void> deleteIngredient(@PathVariable("foodId") String foodId,
                                              @PathVariable("ingredientId") String ingredientId) {
        foodIngredientService.deleteFoodIngredient(foodId, ingredientId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa nguyên liệu khỏi món ăn thành công")
                .build();
    }

    @GetMapping("/{foodId}/ingredients")
    public ApiResponse<List<FoodIngredientResponse>> viewIngredientsByFood(@PathVariable("foodId") String foodId) {
        return ApiResponse.<List<FoodIngredientResponse>>builder()
                .message("Nguyên liệu được sử dụng trong món ăn")
                .data(foodIngredientService.viewIngredientByFood(foodId))
                .build();
    }
}
