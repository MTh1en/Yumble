package com.mthien.yumble.controller;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("food")
public class FoodController {
    private final FoodService foodService;
    private final FoodRepo foodRepo;

    public FoodController(FoodService foodService, FoodRepo foodRepo) {
        this.foodService = foodService;
        this.foodRepo = foodRepo;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<FoodResponse>> create(@RequestBody CreateFoodRequest request) {
        var data = foodService.create(request);
        return ResponseEntity.ok(ApiResponse.<FoodResponse>builder()
                .code(200)
                .message("Tạo món ăn mới thành công")
                .data(data)
                .build());
    }

    @GetMapping("view-one/{foodId}")
    public ResponseEntity<ApiResponse<FoodResponse>> viewOne(@PathVariable("foodId") String foodId) {
        var data = foodService.viewOne(foodId);
        return ResponseEntity.ok(ApiResponse.<FoodResponse>builder()
                .code(200)
                .message("Thông tin món ăn")
                .data(data)
                .build());
    }

    @GetMapping("test/{id}")
    public Set<Dietary> test(@PathVariable("id") String id) {
        return foodRepo.findDietaryByFoodId(id);
    }
}
