package com.mthien.yumble.controller;

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
    public ApiResponse<PremiumResponse> initPremium(@PathVariable("userId") String id) {
        return ApiResponse.<PremiumResponse>builder()
                .message("Khởi tạo Premium thành công")
                .data(premiumService.initPremium(id))
                .build();
    }

    @PutMapping("{userId}/premium")
    public ApiResponse<PremiumResponse> updatePremium(@PathVariable("userId") String id) {
        return ApiResponse.<PremiumResponse>builder()
                .message("Cập nhật Premium thành công")
                .data(premiumService.updatePremium(id))
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
