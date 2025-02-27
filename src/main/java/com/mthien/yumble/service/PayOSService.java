package com.mthien.yumble.service;

import com.mthien.yumble.entity.Enum.PaymentStatus;
import com.mthien.yumble.entity.Enum.PremiumStatus;
import com.mthien.yumble.entity.Payment;
import com.mthien.yumble.entity.Premium;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.payload.request.payos.CreatePaymentLink;
import com.mthien.yumble.repository.PaymentRepo;
import com.mthien.yumble.repository.PremiumRepo;
import com.mthien.yumble.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PayOSService {
    @Value("${payos.clientId}")
    protected String CLIENT_ID;
    @Value("${payos.apiKey}")
    protected String API_KEY;
    @Value("${payos.checksumKey}")
    protected String CHECK_SUM_KEY;
    @Value("${app.base.url}")
    protected String BASE_URL;
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    private final Random random = new SecureRandom();

    private final UserRepo userRepo;
    private final PremiumRepo premiumRepo;
    private final PaymentRepo paymentRepo;

    public CheckoutResponseData createPaymentLink(CreatePaymentLink request) throws Exception {
        PayOS payOS = new PayOS(CLIENT_ID, API_KEY, CHECK_SUM_KEY);
        Long orderCode = Math.abs(random.nextLong() % 10_000_000_000L);
        Users users = userRepo.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        ItemData itemData = (request.getIsMonthPremium())
                ? ItemData.builder().name("Premium 1 tháng").price(59000).quantity(1).build()
                : ItemData.builder().name("Premium 1 năm").price(599000).quantity(1).build();
        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCode)
                .amount(itemData.getPrice())
                .buyerName(users.getName())
                .buyerEmail(users.getEmail())
                .buyerAddress(users.getAddress())
                .description(itemData.getName())
                .returnUrl(BASE_URL + "/payos/success")
                .cancelUrl(BASE_URL + "/payos/fail/" + orderCode)
                .item(itemData)
                .signature(SIGNER_KEY)
                .build();
        Payment payment = Payment.builder()
                .premium(premiumRepo.findByUsers(users).orElseThrow(() -> new AppException(ErrorCode.PREMIUM_NOT_FOUND)))
                .code(orderCode)
                .amount(itemData.getPrice())
                .time(LocalDateTime.now())
                .status(PaymentStatus.PENDING)
                .build();
        paymentRepo.save(payment);
        return payOS.createPaymentLink(paymentData);
    }

    public PaymentLinkData getPaymentLink(long orderCode) {
        try {
            PayOS payOS = new PayOS(CLIENT_ID, API_KEY, CHECK_SUM_KEY);
            return payOS.getPaymentLinkInformation(orderCode);
        } catch (Exception e) {
            throw new AppException(ErrorCode.PAYMENT_NOT_FOUND);
        }
    }

    public WebhookData verifyWebhookData(Webhook webhookBody) throws Exception {
        PayOS payOS = new PayOS(CLIENT_ID, API_KEY, CHECK_SUM_KEY);
        return payOS.verifyPaymentWebhookData(webhookBody);
    }

    public void updateWebHookPayment(WebhookData webhookData) {
        Payment payment = paymentRepo.findByCode(webhookData.getOrderCode())
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        if (webhookData.getCode().equals("00")) {
            payment.setStatus(PaymentStatus.SUCCESS);
        } else {
            payment.setStatus(PaymentStatus.FAIL);
        }
        payment.setTime(LocalDateTime.now());
        payment.setAmount(webhookData.getAmount());
        paymentRepo.save(payment);

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            Premium premium = premiumRepo.findById(payment.getPremium().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.PREMIUM_NOT_FOUND));
            premium.setPremiumStatus(PremiumStatus.ACTIVE);
            premium.setStart(LocalDateTime.now());
            LocalDateTime end;
            if (webhookData.getAmount() == 59000) {
                end = LocalDateTime.now().plusMonths(1);
            } else if (webhookData.getAmount() == 599000) {
                end = LocalDateTime.now().plusYears(1);
            } else {
                throw new AppException(ErrorCode.PAYMENT_NOT_FOUND);
            }
            premium.setEnd(end);
            premium.setRemaining(LocalDateTime.now().until(end, ChronoUnit.DAYS));
            premiumRepo.save(premium);
        }
    }

    public PaymentLinkData cancelPayOS(long orderCode) throws Exception {
        PayOS payOS = new PayOS(CLIENT_ID, API_KEY, CHECK_SUM_KEY);
        return payOS.cancelPaymentLink(orderCode, "Hủy đơn hàng");
    }

    public void cancelPayment(long orderCode){
        Payment payment = paymentRepo.findByCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        payment.setStatus(PaymentStatus.FAIL);
        payment.setTime(LocalDateTime.now());
        paymentRepo.save(payment);
    }

    public String confirmWebhookUrl(String webhookUrl) throws Exception {
        PayOS payOS = new PayOS(CLIENT_ID, API_KEY, CHECK_SUM_KEY);
        return payOS.confirmWebhook(webhookUrl); // Kiểm tra tính hợp lệ của URL webhook
    }
}
