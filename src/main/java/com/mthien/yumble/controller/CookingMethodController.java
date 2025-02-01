package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.cookingmethod.CreateCookingMethodRequest;
import com.mthien.yumble.payload.request.cookingmethod.UpdateCookingMethodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import com.mthien.yumble.service.CookingMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cooking-methods")
@RequiredArgsConstructor
public class CookingMethodController {
    private final CookingMethodService cookingMethodService;

    @PostMapping()
    public ApiResponse<CookingMethodResponse> create(@RequestBody CreateCookingMethodRequest request) {
        return ApiResponse.<CookingMethodResponse>builder()
                .message("Tạo phương pháp chế biến mới thành công")
                .data(cookingMethodService.create(request))
                .build();
    }

    @PutMapping("{id}")
    public ApiResponse<CookingMethodResponse> update(@PathVariable("id") String id,
                                                     @RequestBody UpdateCookingMethodRequest request) {
        return ApiResponse.<CookingMethodResponse>builder()
                .message("Cập nhật thông tin phương pháp chế biến thàng công")
                .data(cookingMethodService.update(id, request))
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<CookingMethodResponse> viewOne(@PathVariable("id") String id) {
        return ApiResponse.<CookingMethodResponse>builder()
                .message("Thông tin của phương pháp chế biến")
                .data(cookingMethodService.viewOne(id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<CookingMethodResponse>> viewAll() {
        return ApiResponse.<List<CookingMethodResponse>>builder()
                .message("Thông tin tất cả các phương pháp chế biến")
                .data(cookingMethodService.viewAll())
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        cookingMethodService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Đã xóa phương pháp chế biến thành công")
                .build();
    }
}
