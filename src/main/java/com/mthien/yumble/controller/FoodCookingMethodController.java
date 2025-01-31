package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.cookingmethod.AddFoodCookingMethodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodDetailResponse;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodResponse;
import com.mthien.yumble.service.FoodCookingMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods")
public class FoodCookingMethodController {
    private final FoodCookingMethodService foodCookingMethodService;

    public FoodCookingMethodController(FoodCookingMethodService foodCookingMethodService) {
        this.foodCookingMethodService = foodCookingMethodService;
    }

    @PostMapping("/{foodId}/cooking-methods")
    public ResponseEntity<ApiResponse<FoodCookingMethodDetailResponse>> addCookingMethod(@PathVariable("foodId") String foodId,
                                                                                         @RequestBody AddFoodCookingMethodRequest request) {
        var data = foodCookingMethodService.addFoodCookingMethod(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodCookingMethodDetailResponse>builder()
                .code(200)
                .message("Thêm phương pháp biến vào món ăn thành công")
                .data(data)
                .build());
    }

    @DeleteMapping("/{foodId}/cooking-methods/{cookingMethodId}")
    public ResponseEntity<ApiResponse<Void>> deleteCookingMethod(@PathVariable("foodId") String foodId,
                                                                 @PathVariable("cookingMethodId") String cookingMethodId) {
        foodCookingMethodService.deleteFoodCookingMethod(foodId, cookingMethodId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa phương pháp chế biến khỏi món ăn thành công")
                .build());
    }

    @GetMapping("/{foodId}/cooking-methods/")
    public ResponseEntity<ApiResponse<List<FoodCookingMethodResponse>>> viewCookingMethodsByFood(@PathVariable("foodId") String foodId) {
        var data = foodCookingMethodService.viewCookingMethodsByFood(foodId);
        return ResponseEntity.ok(ApiResponse.<List<FoodCookingMethodResponse>>builder()
                .code(200)
                .message("Phương pháp chế biến của món ăn")
                .data(data)
                .build());
    }
}