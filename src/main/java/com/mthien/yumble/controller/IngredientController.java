package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.ingredient.CreateIngredientRequest;
import com.mthien.yumble.payload.request.ingredient.UpdateIngredientRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import com.mthien.yumble.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping()
    public ApiResponse<IngredientResponse> create(@RequestBody CreateIngredientRequest request) {
        return ApiResponse.<IngredientResponse>builder()
                .message("Tạo nguyên liệu thành công")
                .data(ingredientService.create(request))
                .build();
    }

    @PutMapping("{ingredientId}")
    public ApiResponse<IngredientResponse> update(@PathVariable("ingredientId") String ingredientId,
                                                  @RequestBody UpdateIngredientRequest request) {
        return ApiResponse.<IngredientResponse>builder()
                .message("Cập nhật nguyên liệu thành công")
                .data(ingredientService.update(ingredientId, request))
                .build();
    }

    @GetMapping("{ingredientId}")
    public ApiResponse<IngredientResponse> viewOne(@PathVariable("ingredientId") String ingredientId) {
        return ApiResponse.<IngredientResponse>builder()
                .message("Thông tin nguyên liệu")
                .data(ingredientService.viewOne(ingredientId))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<IngredientResponse>> viewAll() {
        return ApiResponse.<List<IngredientResponse>>builder()
                .message("Thông tin tất cả nguyên liệu")
                .data(ingredientService.viewAll())
                .build();
    }

    @DeleteMapping("{ingredientId}")
    public ApiResponse<Void> delete(@PathVariable("ingredientId") String ingredientId) {
        ingredientService.delete(ingredientId);
        return ApiResponse.<Void>builder()
                .message("Thông tin nguyên liệu được xóa thành công")
                .build();
    }
}
