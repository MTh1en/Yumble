package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.ingredient.CreateIngredientRequest;
import com.mthien.yumble.payload.request.ingredient.UpdateIngredientRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import com.mthien.yumble.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<IngredientResponse>> create(@RequestBody CreateIngredientRequest request) {
        var data = ingredientService.create(request);
        return ResponseEntity.ok(ApiResponse.<IngredientResponse>builder()
                .code(200)
                .message("Tạo nguyên liệu thành công")
                .data(data)
                .build());
    }

    @PutMapping("{ingredientId}")
    public ResponseEntity<ApiResponse<IngredientResponse>> update(@PathVariable("ingredientId") String ingredientId,
                                                                  @RequestBody UpdateIngredientRequest request) {
        var data = ingredientService.update(ingredientId, request);
        return ResponseEntity.ok(ApiResponse.<IngredientResponse>builder()
                .code(200)
                .message("Cập nhật nguyên liệu thành công")
                .data(data)
                .build());
    }

    @GetMapping("{ingredientId}")
    public ResponseEntity<ApiResponse<IngredientResponse>> viewOne(@PathVariable("ingredientId") String ingredientId) {
        var data = ingredientService.viewOne(ingredientId);
        return ResponseEntity.ok(ApiResponse.<IngredientResponse>builder()
                .code(200)
                .message("Thông tin nguyên liệu")
                .data(data)
                .build());
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<IngredientResponse>>> viewAll() {
        var data = ingredientService.viewAll();
        return ResponseEntity.ok(ApiResponse.<List<IngredientResponse>>builder()
                .code(200)
                .message("Thông tin tất cả nguyên liệu")
                .data(data)
                .build());
    }

    @DeleteMapping("{ingredientId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("ingredientId") String ingredientId) {
        ingredientService.delete(ingredientId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Thông tin nguyên liệu được xóa thành công")
                .build());
    }
}
