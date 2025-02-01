package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.allergy.CreateAllergyRequest;
import com.mthien.yumble.payload.request.allergy.UpdateAllergyRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.service.AllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("allergies")
@RequiredArgsConstructor
public class AllergyController {
    private final AllergyService allergyService;

    @PostMapping()
    public ApiResponse<AllergyResponse> create(@RequestBody CreateAllergyRequest request) {
        return ApiResponse.<AllergyResponse>builder()
                .message("Tạo Thành phần dị ứng thành công")
                .data(allergyService.create(request))
                .build();
    }

    @PutMapping("{id}")
    public ApiResponse<AllergyResponse> update(@PathVariable("id") String id,
                                               @RequestBody UpdateAllergyRequest request) {
        return ApiResponse.<AllergyResponse>builder()
                .message("Cập nhật thông tin thành phần dị ứng thành công")
                .data(allergyService.update(id, request))
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<AllergyResponse> viewOne(@PathVariable("id") String id) {
        return ApiResponse.<AllergyResponse>builder()
                .message("Thông tin của thành phần dịch ứng")
                .data(allergyService.viewOne(id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<AllergyResponse>> viewAll() {
        return ApiResponse.<List<AllergyResponse>>builder()
                .message("Danh sách tất cả thành phần dị dứng")
                .data(allergyService.viewAll())
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        allergyService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Xóa thông tin thành phần dị ứng thành công")
                .build();
    }
}
