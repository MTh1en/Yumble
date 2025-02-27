package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.payos.CreatePaymentLink;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.service.PayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentLinkData;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

@RestController
@RequestMapping("payos")
@RequiredArgsConstructor
public class PayOSController {
    private final PayOSService payOSService;

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

    @PostMapping("/handle-webhook")
    public ApiResponse<String> handleWebHook(@RequestBody Webhook webhookBody) throws Exception {
        WebhookData webhookData = payOSService.verifyWebhookData(webhookBody);
        payOSService.updateWebHookPayment(webhookData);
        return ApiResponse.<String>builder()
                .message("Handle Webhook thành công")
                .data("Webhook verified: " + webhookData.getOrderCode())
                .build();

    }

    @PostMapping("/register")
    public ApiResponse<String> registerWebhookUrl(@RequestBody String webhookUrl) throws Exception {
        String result = payOSService.confirmWebhookUrl(webhookUrl);
        return ApiResponse.<String>builder()
                .message("Đăng ký webhook thành công")
                .data("Webhook URL registered successfully: " + result)
                .build();
    }

    @GetMapping("/success")
    public ModelAndView paymentSuccess() {
        return new ModelAndView("PaymentSuccess");
    }

    @PostMapping("cancel/{orderCode}")
    public ApiResponse<PaymentLinkData> cancelPayment(@PathVariable long orderCode) throws Exception {
        return ApiResponse.<PaymentLinkData>builder()
                .message("Đã hủy thanh toán thành công")
                .data(payOSService.cancelPayOS(orderCode))
                .build();
    }

    @GetMapping("/fail/{orderCode}")
    public ModelAndView paymentFail(@PathVariable("orderCode") long orderCode){
        payOSService.cancelPayment(orderCode);
        return new ModelAndView("PaymentFail");
    }
}
