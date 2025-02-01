package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.premium.CreatePremiumRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.premium.PremiumResponse;
import com.mthien.yumble.service.PremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class PremiumController {
    private final PremiumService premiumService;

    @PostMapping("/{userId}/premium")
    public ApiResponse<PremiumResponse> createPremium(@PathVariable("userId") String id,
                                                      @RequestBody CreatePremiumRequest request) {
        return ApiResponse.<PremiumResponse>builder()
                .message("Bạn đã đăng ký Premium thành công")
                .data(premiumService.createPremium(id, request))
                .build();
    }

    @GetMapping("/{userId}/premium/remaining")
    public ApiResponse<PremiumResponse> calculateRemaining(@PathVariable("userId") String userId) {
        return ApiResponse.<PremiumResponse>builder()
                .message("Cập nhật thời gian Premium còn lại thành công")
                .data(premiumService.calculateRemaining(userId))
                .build();
    }

    @GetMapping("/{userId}/premium")
    public ApiResponse<PremiumResponse> viewPremiumByUser(@PathVariable("userId") String userId) {
        return ApiResponse.<PremiumResponse>builder()
                .message("Thông tin Premium của tài khoản")
                .data(premiumService.viewPremium(userId))
                .build();
    }
}
