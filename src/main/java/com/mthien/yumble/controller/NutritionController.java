package com.mthien.yumble.controller;

import com.mthien.yumble.entity.Nutrition;
import com.mthien.yumble.payload.request.nutrition.CreateNutritionRequest;
import com.mthien.yumble.payload.request.nutrition.UpdateNutritionRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.nutrition.NutritionResponse;
import com.mthien.yumble.service.NutritionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("nutrition")
public class NutritionController {
    private final NutritionService nutritionService;

    public NutritionController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    @PostMapping("create/{foodId}")
    public ResponseEntity<ApiResponse<NutritionResponse>> create(@PathVariable("foodId") String id,
                                                                 @RequestBody CreateNutritionRequest request) {
        var data = nutritionService.create(id, request);
        return ResponseEntity.ok(ApiResponse.<NutritionResponse>builder()
                .code(200)
                .message("Tạo thông tin dinh dưỡng cho món ăn thành công")
                .data(data)
                .build());
    }

    @PutMapping("update/{foodId}")
    public ResponseEntity<ApiResponse<NutritionResponse>> update(@PathVariable("foodId") String id,
                                                                 @RequestBody UpdateNutritionRequest request) {
        var data = nutritionService.update(id, request);
        return ResponseEntity.ok(ApiResponse.<NutritionResponse>builder()
                .code(200)
                .message("Cập nhật thông tin dinh dưỡng cho món ăn thành công")
                .data(data)
                .build());
    }

    @GetMapping("view-one/{foodId}")
    public ResponseEntity<ApiResponse<NutritionResponse>> viewOne(@PathVariable("foodId") String id) {
        var data = nutritionService.viewOne(id);
        return ResponseEntity.ok(ApiResponse.<NutritionResponse>builder()
                .code(200)
                .message("thông tin dinh dưỡng cho món ăn")
                .data(data)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<NutritionResponse>>> viewAll() {
        var data = nutritionService.viewAll();
        return ResponseEntity.ok(ApiResponse.<List<NutritionResponse>>builder()
                .code(200)
                .message("thông tin dinh dưỡng cho món ăn")
                .data(data)
                .build());
    }

    @DeleteMapping("delete/{foodId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("foodId") String id) {
        nutritionService.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa thông tin dinh dưỡng thành công")
                .build());
    }

    @GetMapping("view/{foodId}")
    public Nutrition view(@PathVariable("foodId") String id) {
        return nutritionService.view(id);
    }
}
