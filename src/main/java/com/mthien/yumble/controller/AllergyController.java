package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.allergy.CreateAllergyRequest;
import com.mthien.yumble.payload.request.allergy.UpdateAllergyRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.service.AllergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("allergies")
public class AllergyController {
    private final AllergyService allergyService;

    public AllergyController(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<AllergyResponse>> create(@RequestBody CreateAllergyRequest request) {
        var data = allergyService.create(request);
        return ResponseEntity.ok(ApiResponse.<AllergyResponse>builder()
                .code(200)
                .message("Tạo Thành phần dị ứng thành công")
                .data(data)
                .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<AllergyResponse>> update(@PathVariable("id") String id,
                                                               @RequestBody UpdateAllergyRequest request) {
        var data = allergyService.update(id, request);
        return ResponseEntity.ok(ApiResponse.<AllergyResponse>builder()
                .code(200)
                .message("Cập nhật thông tin thành phần dị ứng thành công")
                .data(data)
                .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<AllergyResponse>> viewOne(@PathVariable("id") String id) {
        var data = allergyService.viewOne(id);
        return ResponseEntity.ok(ApiResponse.<AllergyResponse>builder()
                .code(200)
                .message("Thông tin của thành phần dịch ứng")
                .data(data)
                .build());
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<AllergyResponse>>> viewAll() {
        var data = allergyService.viewAll();
        return ResponseEntity.ok(ApiResponse.<List<AllergyResponse>>builder()
                .code(200)
                .message("Danh sách tất cả thành phần dị dứng")
                .data(data)
                .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") String id) {
        allergyService.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa thông tin thành phần dị ứng thành công")
                .build());
    }
}
