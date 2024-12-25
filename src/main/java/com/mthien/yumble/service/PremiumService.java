package com.mthien.yumble.service;

import com.mthien.yumble.entity.Premium;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.PremiumMapper;
import com.mthien.yumble.payload.request.premium.CreatePremiumRequest;
import com.mthien.yumble.payload.response.premium.PremiumResponse;
import com.mthien.yumble.repository.PremiumRepo;
import com.mthien.yumble.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class PremiumService {
    private final PremiumRepo premiumRepo;
    private final PremiumMapper premiumMapper;
    private final UserRepo userRepo;

    public PremiumService(PremiumRepo premiumRepo, PremiumMapper premiumMapper, UserRepo userRepo) {
        this.premiumRepo = premiumRepo;
        this.premiumMapper = premiumMapper;
        this.userRepo = userRepo;
    }

    public PremiumResponse createPremium(String id, CreatePremiumRequest request) {
        Users users = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Premium premium = premiumMapper.createPremium(request);
        premium.setUsers(users);
        return premiumMapper.toPremiumResponse(premiumRepo.save(premium));
    }

    public PremiumResponse calculateRemaining(String id) {
        Users users = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Premium premium = premiumRepo.findByUsers(users).orElseThrow(() -> new AppException(ErrorCode.PREMIUM_NOT_REGISTERED));
        long remaining = LocalDateTime.now().until(premium.getEnd(), ChronoUnit.DAYS);
        if (remaining > 0) {
            premium.setRemaining(remaining);
            return premiumMapper.toPremiumResponse(premiumRepo.save(premium));
        } else {
             premiumRepo.delete(premium);
             throw new AppException(ErrorCode.PREMIUM_EXPIRED);
        }
    }

    public PremiumResponse viewPremium(String id){
        Users users = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Premium premium = premiumRepo.findByUsers(users).orElseThrow(() -> new AppException(ErrorCode.PREMIUM_NOT_REGISTERED));
        return premiumMapper.toPremiumResponse(premium);
    }
}
