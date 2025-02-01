package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.cookingmethod.AddFoodCookingMethodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodDetailResponse;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodResponse;
import com.mthien.yumble.service.FoodCookingMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods")
@RequiredArgsConstructor
public class FoodCookingMethodController {
    private final FoodCookingMethodService foodCookingMethodService;

    @PostMapping("/{foodId}/cooking-methods")
    public ApiResponse<FoodCookingMethodDetailResponse> addCookingMethod(@PathVariable("foodId") String foodId,
                                                                         @RequestBody AddFoodCookingMethodRequest request) {
        return ApiResponse.<FoodCookingMethodDetailResponse>builder()
                .message("Thêm phương pháp biến vào món ăn thành công")
                .data(foodCookingMethodService.addFoodCookingMethod(foodId, request))
                .build();
    }

    @DeleteMapping("/{foodId}/cooking-methods/{cookingMethodId}")
    public ApiResponse<Void> deleteCookingMethod(@PathVariable("foodId") String foodId,
                                                 @PathVariable("cookingMethodId") String cookingMethodId) {
        foodCookingMethodService.deleteFoodCookingMethod(foodId, cookingMethodId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa phương pháp chế biến khỏi món ăn thành công")
                .build();
    }

    @GetMapping("/{foodId}/cooking-methods/")
    public ApiResponse<List<FoodCookingMethodResponse>> viewCookingMethodsByFood(@PathVariable("foodId") String foodId) {
        return ApiResponse.<List<FoodCookingMethodResponse>>builder()
                .message("Phương pháp chế biến của món ăn")
                .data(foodCookingMethodService.viewCookingMethodsByFood(foodId))
                .build();
    }
}