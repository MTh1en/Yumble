package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.nutrition.CreateNutritionRequest;
import com.mthien.yumble.payload.request.nutrition.UpdateNutritionRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.nutrition.NutritionResponse;
import com.mthien.yumble.service.NutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("foods")
@RequiredArgsConstructor
public class NutritionController {
    private final NutritionService nutritionService;

    @PostMapping("/{foodId}/nutrition")
    public ApiResponse<NutritionResponse> create(@PathVariable("foodId") String id,
                                                 @RequestBody CreateNutritionRequest request) {
        return ApiResponse.<NutritionResponse>builder()
                .message("Tạo thông tin dinh dưỡng cho món ăn thành công")
                .data(nutritionService.create(id, request))
                .build();
    }

    @PutMapping("/{foodId}/nutrition")
    public ApiResponse<NutritionResponse> update(@PathVariable("foodId") String id,
                                                 @RequestBody UpdateNutritionRequest request) {
        return ApiResponse.<NutritionResponse>builder()
                .message("Cập nhật thông tin dinh dưỡng cho món ăn thành công")
                .data(nutritionService.update(id, request))
                .build();
    }

    @GetMapping("/{foodId}/nutrition")
    public ApiResponse<NutritionResponse> viewOne(@PathVariable("foodId") String id) {
        return ApiResponse.<NutritionResponse>builder()
                .message("thông tin dinh dưỡng cho món ăn")
                .data(nutritionService.viewByFood(id))
                .build();
    }

    @GetMapping("/nutrition")
    public ApiResponse<List<NutritionResponse>> viewAll() {
        return ApiResponse.<List<NutritionResponse>>builder()
                .message("thông tin dinh dưỡng cho món ăn")
                .data(nutritionService.viewAll())
                .build();
    }

    @DeleteMapping("/{foodId}/nutrition")
    public ApiResponse<Void> delete(@PathVariable("foodId") String id) {
        nutritionService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Xóa thông tin dinh dưỡng thành công")
                .build();
    }
}
