package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.premium.CreatePremiumRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.premium.PremiumResponse;
import com.mthien.yumble.service.PremiumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PremiumController {
    private final PremiumService premiumService;

    public PremiumController(PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @PostMapping("users/{userId}/premium")
    public ResponseEntity<ApiResponse<PremiumResponse>> createPremium(@PathVariable("userId") String id,
                                                                          @RequestBody CreatePremiumRequest request) {
        var data = premiumService.createPremium(id, request);
        return ResponseEntity.ok(ApiResponse.<PremiumResponse>builder()
                .code(200)
                .message("Bạn đã đăng ký Premium thành công")
                .data(data)
                .build());
    }

    @GetMapping("users/{userId}/premium/remaining")
    public ResponseEntity<ApiResponse<PremiumResponse>> calculateRemaining(@PathVariable("userId") String id) {
        var data = premiumService.calculateRemaining(id);
        return ResponseEntity.ok(ApiResponse.<PremiumResponse>builder()
                .code(200)
                .message("Cập nhật thời gian Premium còn lại thành công")
                .data(data)
                .build());
    }

    @GetMapping("users/{userId}/premium")
    public ResponseEntity<ApiResponse<PremiumResponse>> viewPremiumByUser(@PathVariable("userId") String id) {
        var data = premiumService.viewPremium(id);
        return ResponseEntity.ok(ApiResponse.<PremiumResponse>builder()
                .code(200)
                .message("Thông tin Premium của tài khoản")
                .data(data)
                .build());
    }
}
