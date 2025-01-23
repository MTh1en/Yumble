package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.cookingmethod.CreateCookingMethodRequest;
import com.mthien.yumble.payload.request.cookingmethod.UpdateCookingMethodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import com.mthien.yumble.service.CookingMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cooking-methods")
public class CookingMethodController {
    private final CookingMethodService cookingMethodService;

    public CookingMethodController(CookingMethodService cookingMethodService) {
        this.cookingMethodService = cookingMethodService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<CookingMethodResponse>> create(@RequestBody CreateCookingMethodRequest request) {
        var data = cookingMethodService.create(request);
        return ResponseEntity.ok(ApiResponse.<CookingMethodResponse>builder()
                .code(200)
                .message("Tạo phương pháp chế biến mới thành công")
                .data(data)
                .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CookingMethodResponse>> update(@PathVariable("id") String id,
                                                                     @RequestBody UpdateCookingMethodRequest request) {
        var data = cookingMethodService.update(id, request);
        return ResponseEntity.ok(ApiResponse.<CookingMethodResponse>builder()
                .code(200)
                .message("Cập nhật thông tin phương pháp chế biến thàng công")
                .data(data)
                .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CookingMethodResponse>> viewOne(@PathVariable("id") String id) {
        var data = cookingMethodService.viewOne(id);
        return ResponseEntity.ok(ApiResponse.<CookingMethodResponse>builder()
                .code(200)
                .message("Thông tin của phương pháp chế biến")
                .data(data)
                .build());
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CookingMethodResponse>>> viewAll() {
        var data = cookingMethodService.viewAll();
        return ResponseEntity.ok(ApiResponse.<List<CookingMethodResponse>>builder()
                .code(200)
                .message("Thông tin tất cả các phương pháp chế biến")
                .data(data)
                .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") String id) {
        cookingMethodService.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa phương pháp chế biến thành công")
                .build());
    }
}
