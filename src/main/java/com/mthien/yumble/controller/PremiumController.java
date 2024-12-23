package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.premium.CalculatePremiumRequest;
import com.mthien.yumble.payload.request.premium.CreatePremiumRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.premium.PremiumResponse;
import com.mthien.yumble.service.PremiumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("premium")
public class PremiumController {
    private final PremiumService premiumService;

    public PremiumController(PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<PremiumResponse>> createPremium(@RequestBody CreatePremiumRequest request) {
        var data = premiumService.createPremium(request);
        return ResponseEntity.ok(ApiResponse.<PremiumResponse>builder()
                .code(200)
                .message("Bạn đã đăng ký Premium thành công")
                .data(data)
                .build());
    }

    @PutMapping("calculate-remaining")
    public ResponseEntity<ApiResponse<PremiumResponse>> calculateRemaining(@RequestBody CalculatePremiumRequest request) {
        var data = premiumService.calculateRemaining(request);
        return ResponseEntity.ok(ApiResponse.<PremiumResponse>builder()
                .code(200)
                .message("Cập nhật thời gian Premium còn lại thành công")
                .data(data)
                .build());
    }

    @GetMapping("view-by-user")
    public ResponseEntity<ApiResponse<PremiumResponse>> viewPremiumByUser(@RequestParam("userId") String userId) {
        var data = premiumService.viewPremium(userId);
        return ResponseEntity.ok(ApiResponse.<PremiumResponse>builder()
                .code(200)
                .message("Thông tin Premium của tài khoản")
                .data(data)
                .build());
    }
}
