package com.mthien.yumble.controller;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("food")
public class FoodController {
    private static final Logger log = LoggerFactory.getLogger(FoodController.class);
    private final FoodService foodService;
    private final FoodRepo foodRepo;

    public FoodController(FoodService foodService, FoodRepo foodRepo) {
        this.foodService = foodService;
        this.foodRepo = foodRepo;
    }

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FoodResponse>> create(@ModelAttribute CreateFoodRequest request) {
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

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<Set<FoodResponse>>> viewAll() {
        var data = foodService.viewAll();
        return ResponseEntity.ok(ApiResponse.<Set<FoodResponse>>builder()
                .code(200)
                .message("Thông tin món ăn")
                .data(data)
                .build());
    }

//    @GetMapping("test/{id}")
//    public Set<Food> test(@PathVariable("id") String id) {
//        return foodRepo.findFoodId(id);
//    }
}
