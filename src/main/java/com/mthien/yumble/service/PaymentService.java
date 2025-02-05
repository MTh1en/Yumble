package com.mthien.yumble.service;

import com.mthien.yumble.entity.Enum.PaymentStatus;
import com.mthien.yumble.entity.Payment;
import com.mthien.yumble.entity.Premium;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.repository.PaymentRepo;
import com.mthien.yumble.repository.PremiumRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepo paymentRepo;
    private final PremiumRepo premiumRepo;
    private final PayOSService payOSService;

    public Payment checkPaymentStatus(String premiumId) {
        Premium premium = premiumRepo.findById(premiumId).orElseThrow(() -> new AppException(ErrorCode.PREMIUM_NOT_REGISTERED));
        paymentRepo.findByPremiumAndStatus(premium, PaymentStatus.PENDING)
                .forEach(payment -> {
                    String check = payOSService.getPaymentLink(payment.getCode()).getStatus();
                    switch (check) {
                        case "PAID" -> {
                            payment.setStatus(PaymentStatus.SUCCESS);
                            paymentRepo.save(payment);
                        }
                        case "EXPIRED", "CANCELLED" -> {
                            payment.setStatus(PaymentStatus.FAIL);
                            paymentRepo.save(payment);
                        }
                    }
                });
        return paymentRepo.findByPremiumAndStatusOrderByTimeDesc(premium, PaymentStatus.SUCCESS).orElse(null);
    }
}
