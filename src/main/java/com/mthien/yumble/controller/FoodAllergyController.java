package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.foodallergy.AddAllergiesRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.foodallergy.FoodAllergyResponse;
import com.mthien.yumble.service.FoodAllergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("FoodAllergy")
public class FoodAllergyController {
    private final FoodAllergyService foodAllergyService;

    public FoodAllergyController(FoodAllergyService foodAllergyService) {
        this.foodAllergyService = foodAllergyService;
    }

    @PostMapping("add-allergies/{foodId}")
    public ResponseEntity<ApiResponse<FoodAllergyResponse>> addAllergies(@PathVariable String foodId,
                                                                         @RequestBody AddAllergiesRequest request) {
        var data = foodAllergyService.addFoodAllergy(foodId, request);
        return ResponseEntity.ok(ApiResponse.<FoodAllergyResponse>builder()
                .code(200)
                .message("Thêm thành phần dị ứng thành công")
                .data(data)
                .build());
    }
}
