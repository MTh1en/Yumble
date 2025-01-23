package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.dietary.CreateDietaryRequest;
import com.mthien.yumble.payload.request.dietary.UpdateDietaryRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import com.mthien.yumble.service.DietaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dietaries")
public class DietaryController {
    private final DietaryService dietaryService;

    public DietaryController(DietaryService dietaryService) {
        this.dietaryService = dietaryService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<DietaryResponse>> create(@RequestBody CreateDietaryRequest request) {
        var data = dietaryService.create(request);
        return ResponseEntity.ok(ApiResponse.<DietaryResponse>builder()
                .code(200)
                .message("Tạo chế độ ăn mới thành công")
                .data(data)
                .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<DietaryResponse>> update(@PathVariable("id") String id,
                                                               @RequestBody UpdateDietaryRequest request) {
        var data = dietaryService.update(id, request);
        return ResponseEntity.ok(ApiResponse.<DietaryResponse>builder()
                .code(200)
                .message("Cập nhật thông tin chế độ ăn thàng công")
                .data(data)
                .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<DietaryResponse>> viewOne(@PathVariable("id") String id) {
        var data = dietaryService.viewOne(id);
        return ResponseEntity.ok(ApiResponse.<DietaryResponse>builder()
                .code(200)
                .message("Thông tin của chế độ ăn uống")
                .data(data)
                .build());
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<DietaryResponse>>> viewAll() {
        var data = dietaryService.viewAll();
        return ResponseEntity.ok(ApiResponse.<List<DietaryResponse>>builder()
                .code(200)
                .message("Thông tin tất cả các chế độ ăn uống")
                .data(data)
                .build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") String id) {
        dietaryService.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa chế độ ăn uống thành công")
                .build());
    }
}
