package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.request.food.UpdateFoodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.service.FoodService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("food")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FoodResponse>> create(@ModelAttribute CreateFoodRequest request) throws IOException {
        var data = foodService.create(request);
        return ResponseEntity.ok(ApiResponse.<FoodResponse>builder()
                .code(200)
                .message("Tạo món ăn mới thành công")
                .data(data)
                .build());
    }

    @PutMapping(value = "update/{foodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FoodResponse>> update(@PathVariable("foodId") String id,
                                                            @ModelAttribute UpdateFoodRequest request) throws IOException {
        var data = foodService.update(id, request);
        return ResponseEntity.ok(ApiResponse.<FoodResponse>builder()
                .code(200)
                .message("Cập nhật thông tin món ăn thành công")
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

    @DeleteMapping("delete/{foodId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("foodId") String foodId) {
        foodService.delete(foodId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa thông tin món ăn thành công")
                .build());
    }
}
