package com.mthien.yumble.service;

import com.mthien.yumble.entity.Enum.PremiumStatus;
import com.mthien.yumble.entity.Payment;
import com.mthien.yumble.entity.Premium;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.PremiumMapper;
import com.mthien.yumble.payload.response.premium.PremiumResponse;
import com.mthien.yumble.repository.PremiumRepo;
import com.mthien.yumble.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PremiumService {
    private final PremiumRepo premiumRepo;
    private final PremiumMapper premiumMapper;
    private final UserRepo userRepo;
    private final PaymentService paymentService;

    public PremiumResponse initPremium(String userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Premium premium = Premium.builder()
                .users(users)
                .premiumStatus(PremiumStatus.INACTIVE)
                .build();
        return premiumMapper.toPremiumResponse(premiumRepo.save(premium));
    }

    public PremiumResponse updatePremium(String userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Premium premium = premiumRepo.findByUsers(users).orElseThrow(() -> new AppException(ErrorCode.PREMIUM_NOT_REGISTERED));
        Payment payment = paymentService.checkPaymentStatus(premium.getId());
        if (!Objects.isNull(payment)) {
            if (payment.getAmount() == 59000) {
                premium.setStart(payment.getTime());
                premium.setEnd(payment.getTime().plusMonths(1));
            } else if (payment.getAmount() == 599000) {
                premium.setStart(payment.getTime());
                premium.setEnd(payment.getTime().plusYears(1));
            }
            premium.setPremiumStatus(PremiumStatus.ACTIVE);
            calculateRemaining(userId);
        }
        return premiumMapper.toPremiumResponse(premiumRepo.save(premium));
    }

    public PremiumResponse calculateRemaining(String userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Premium premium = premiumRepo.findByUsers(users).orElseThrow(() -> new AppException(ErrorCode.PREMIUM_NOT_REGISTERED));
        long remaining = LocalDateTime.now().until(premium.getEnd(), ChronoUnit.DAYS);
        if (remaining > 0) {
            premium.setRemaining(remaining);
        } else {
            premium.setPremiumStatus(PremiumStatus.INACTIVE);
        }
        return premiumMapper.toPremiumResponse(premiumRepo.save(premium));
    }

    public PremiumResponse viewPremium(String userId) {
        Users users = userRepo.findById(userId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Premium premium = premiumRepo.findByUsers(users).orElseThrow(() -> new AppException(ErrorCode.PREMIUM_NOT_REGISTERED));
        calculateRemaining(userId);
        return premiumMapper.toPremiumResponse(premium);
    }
}
