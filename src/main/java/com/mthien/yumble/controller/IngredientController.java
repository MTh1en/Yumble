package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.ingredient.CreateIngredientRequest;
import com.mthien.yumble.payload.request.ingredient.UpdateIngredientRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.ingredient.FoodIngredientResponse;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import com.mthien.yumble.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping("/{foodId}/ingredients")
    public ApiResponse<IngredientResponse> createIngredient(@PathVariable("foodId") String foodId,
                                                            @RequestBody CreateIngredientRequest request) {
        return ApiResponse.<IngredientResponse>builder()
                .message("Thêm nguyên liệu vào món ăn thành công")
                .data(ingredientService.addIngredientIntoFood(foodId, request))
                .build();
    }

    @PutMapping("/ingredients/{ingredientId}")
    public ApiResponse<IngredientResponse> updateIngredient(@PathVariable("ingredientId") String ingredientId,
                                                            @RequestBody UpdateIngredientRequest request) {
        return ApiResponse.<IngredientResponse>builder()
                .message("Cập nhật thông tin nguyên liệu thành công")
                .data(ingredientService.updateIngredient(ingredientId, request))
                .build();
    }

    @DeleteMapping("/ingredients/{ingredientId}")
    public ApiResponse<IngredientResponse> deleteIngredient(@PathVariable("ingredientId") String ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ApiResponse.<IngredientResponse>builder()
                .message("Xóa nguyên liệu khỏi món ăn thành công")
                .build();
    }

    @GetMapping("/{foodId}/ingredients")
    public ApiResponse<List<FoodIngredientResponse>> viewIngredientsByFood(@PathVariable("foodId") String foodId) {
        return ApiResponse.<List<FoodIngredientResponse>>builder()
                .message("Thông tin nguyên liệu cần có của món ăn")
                .data(ingredientService.viewIngredientsByFood(foodId))
                .build();
    }
}
