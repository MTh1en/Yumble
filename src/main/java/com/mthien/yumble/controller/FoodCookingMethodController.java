package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.cookingmethod.AddCookingMethodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.cookingmethod.FoodCookingMethodResponse;
import com.mthien.yumble.service.FoodCookingMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodCookingMethodController {
    private final FoodCookingMethodService foodCookingMethodService;

    public FoodCookingMethodController(FoodCookingMethodService foodCookingMethodService) {
        this.foodCookingMethodService = foodCookingMethodService;
    }

    @PostMapping("foods/{foodId}/cooking-methods")
    public ResponseEntity<ApiResponse<FoodCookingMethodResponse>> addCookingMethod(@PathVariable("foodId") String foodId,
                                                                                   @RequestBody AddCookingMethodRequest request) {
        var data = foodCookingMethodService.addCookingMethod(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodCookingMethodResponse>builder()
                .code(200)
                .message("Thêm phương pháp biến vào món ăn thành công")
                .data(data)
                .build());
    }

    @DeleteMapping("foods/{foodId}/cooking-methods/{cookingMethodId}")
    public ResponseEntity<ApiResponse<Void>> deleteCookingMethod(@PathVariable("foodId") String foodId,
                                                                 @PathVariable("cookingMethodId") String cookingMethodId) {
        foodCookingMethodService.deleteCookingMethod(foodId, cookingMethodId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa phương pháp chế biến khỏi món ăn thành công")
                .build());
    }
}