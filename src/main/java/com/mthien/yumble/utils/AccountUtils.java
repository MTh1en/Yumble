package com.mthien.yumble.utils;

import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountUtils {
    private final UserRepo userRepo;

    public Users getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        return userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
    }
}
