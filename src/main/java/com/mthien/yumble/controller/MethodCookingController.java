package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.cookingmethod.CreateCookingMethodRequest;
import com.mthien.yumble.payload.request.cookingmethod.UpdateCookingMethodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.methodcooking.MethodCookingResponse;
import com.mthien.yumble.service.MethodCookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("method-cooking")
public class MethodCookingController {
    private final MethodCookingService methodCookingService;

    public MethodCookingController(MethodCookingService methodCookingService) {
        this.methodCookingService = methodCookingService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<MethodCookingResponse>> create(@RequestBody CreateCookingMethodRequest request) {
        var data = methodCookingService.create(request);
        return ResponseEntity.ok(ApiResponse.<MethodCookingResponse>builder()
                .code(200)
                .message("Tạo phương pháp chế biến mới thành công")
                .data(data)
                .build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<MethodCookingResponse>> update(@PathVariable("id") String id,
                                                               @RequestBody UpdateCookingMethodRequest request) {
        var data = methodCookingService.update(id, request);
        return ResponseEntity.ok(ApiResponse.<MethodCookingResponse>builder()
                .code(200)
                .message("Cập nhật thông tin phương pháp chế biến thàng công")
                .data(data)
                .build());
    }

    @GetMapping("view-one/{id}")
    public ResponseEntity<ApiResponse<MethodCookingResponse>> viewOne(@PathVariable("id") String id) {
        var data = methodCookingService.viewOne(id);
        return ResponseEntity.ok(ApiResponse.<MethodCookingResponse>builder()
                .code(200)
                .message("Thông tin của phương pháp chế biến")
                .data(data)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<MethodCookingResponse>>> viewAll() {
        var data = methodCookingService.viewAll();
        return ResponseEntity.ok(ApiResponse.<List<MethodCookingResponse>>builder()
                .code(200)
                .message("Thông tin tất cả các phương pháp chế biến")
                .data(data)
                .build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") String id) {
        methodCookingService.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa phương pháp chế biến thành công")
                .build());
    }
}
