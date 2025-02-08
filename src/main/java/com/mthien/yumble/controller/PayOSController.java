package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.auth.IntrospectRequest;
import com.mthien.yumble.payload.request.payos.CreatePaymentLink;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.service.PayOSService;
import com.mthien.yumble.service.PremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentLinkData;

@RestController
@RequestMapping("payos")
@RequiredArgsConstructor
public class PayOSController {
    private final PayOSService payOSService;
    private final PremiumService premiumService;

    @PostMapping()
    public ApiResponse<CheckoutResponseData> createPaymentLink(@RequestBody CreatePaymentLink request) throws Exception {
        return ApiResponse.<CheckoutResponseData>builder()
                .message("Tạo link thanh toán thành công")
                .data(payOSService.createPaymentLink(request))
                .build();
    }

    @GetMapping("{orderCode}")
    public ApiResponse<PaymentLinkData> getPaymentLink(@PathVariable long orderCode) {
        return ApiResponse.<PaymentLinkData>builder()
                .message("Thông tin giao dịch")
                .data(payOSService.getPaymentLink(orderCode))
                .build();
    }

    @PostMapping("test")
    public ApiResponse<String> test(@RequestBody IntrospectRequest request) throws Exception {
        return ApiResponse.<String>builder()
                .message("test")
                .data(payOSService.confirmWebhook(request))
                .build();
    }

    @GetMapping("/success/{userId}")
    public ModelAndView paymentSuccess(@PathVariable("userId") String userId) {
        premiumService.activePremium(userId);
        return new ModelAndView("PaymentSuccess");
    }

    @GetMapping("/fail/{userId}")
    public ModelAndView paymentFail(@PathVariable("userId") String userId) {
        premiumService.activePremium(userId);
        return new ModelAndView("PaymentFail");
    }
}
