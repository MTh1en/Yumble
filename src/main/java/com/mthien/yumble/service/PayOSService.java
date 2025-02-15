package com.mthien.yumble.service;

import com.mthien.yumble.entity.Enum.PaymentStatus;
import com.mthien.yumble.entity.Payment;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.payload.request.auth.IntrospectRequest;
import com.mthien.yumble.payload.request.payos.CreatePaymentLink;
import com.mthien.yumble.repository.PaymentRepo;
import com.mthien.yumble.repository.PremiumRepo;
import com.mthien.yumble.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.security.SecureRandom;
import java.time.LocalDateTime;
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
                .returnUrl(BASE_URL + "/payos/success/" + users.getId())
                .cancelUrl(BASE_URL + "/payos/fail/" + users.getId())
                .item(itemData)
                .signature(SIGNER_KEY)
                .build();
        Payment payment = Payment.builder()
                .premium(premiumRepo.findByUsers(users).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND)))
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

    public String confirmWebhook(IntrospectRequest request) throws Exception {
        PayOS payOS = new PayOS(CLIENT_ID, API_KEY, CHECK_SUM_KEY);
        return payOS.confirmWebhook(request.getToken());
    }

}
