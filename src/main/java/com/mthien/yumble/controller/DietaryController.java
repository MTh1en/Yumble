package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.dietary.CreateDietaryRequest;
import com.mthien.yumble.payload.request.dietary.UpdateDietaryRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import com.mthien.yumble.service.DietaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dietaries")
@RequiredArgsConstructor
public class DietaryController {
    private final DietaryService dietaryService;

    @PostMapping()
    public ApiResponse<DietaryResponse> create(@RequestBody CreateDietaryRequest request) {
        return ApiResponse.<DietaryResponse>builder()
                .message("Tạo chế độ ăn mới thành công")
                .data(dietaryService.create(request))
                .build();
    }

    @PutMapping("{id}")
    public ApiResponse<DietaryResponse> update(@PathVariable("id") String id,
                                               @RequestBody UpdateDietaryRequest request) {
        return ApiResponse.<DietaryResponse>builder()
                .message("Cập nhật thông tin chế độ ăn thàng công")
                .data(dietaryService.update(id, request))
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<DietaryResponse> viewOne(@PathVariable("id") String id) {
        return ApiResponse.<DietaryResponse>builder()
                .message("Thông tin của chế độ ăn uống")
                .data(dietaryService.viewOne(id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<DietaryResponse>> viewAll() {
        return ApiResponse.<List<DietaryResponse>>builder()
                .message("Thông tin tất cả các chế độ ăn uống")
                .data(dietaryService.viewAll())
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        dietaryService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Đã xóa chế độ ăn uống thành công")
                .build();
    }
}
