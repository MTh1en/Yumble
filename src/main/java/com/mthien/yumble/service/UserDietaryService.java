package com.mthien.yumble.service;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.UserDietary;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.UserDietaryMapper;
import com.mthien.yumble.payload.request.user.allergy.AddUserDietaryRequest;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryDetailResponse;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryResponse;
import com.mthien.yumble.repository.DietaryRepo;
import com.mthien.yumble.repository.UserDietaryRepo;
import com.mthien.yumble.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDietaryService {
    private final UserDietaryMapper userDietaryMapper;
    private final UserDietaryRepo userDietaryRepo;
    private final UserRepo userRepo;
    private final DietaryRepo dietaryRepo;

    public UserDietaryService(UserDietaryMapper userDietaryMapper, UserDietaryRepo userDietaryRepo, UserRepo userRepo, DietaryRepo dietaryRepo) {
        this.userDietaryMapper = userDietaryMapper;
        this.userDietaryRepo = userDietaryRepo;
        this.userRepo = userRepo;
        this.dietaryRepo = dietaryRepo;
    }

    public UserDietaryDetailResponse addUserDietary(String userId, AddUserDietaryRequest request) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Dietary dietary = dietaryRepo.findById(request.getDietaryId())
                .orElseThrow(() -> new AppException(ErrorCode.DIETARY_NOT_FOUND));
        userDietaryRepo.findByUserAndDietary(users, dietary).ifPresent(existedUserDietary -> {
            throw new AppException(ErrorCode.USER_DIETARY_IS_EXISTED);
        });
        UserDietary userDietary = userDietaryMapper.addUserDietary(users, dietary, request);
        return userDietaryMapper.toUserDietaryDetailResponse(userDietaryRepo.save(userDietary));
    }

    public void deleteUserDietary(String userId, String dietaryId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Dietary dietary = dietaryRepo.findById(dietaryId)
                .orElseThrow(() -> new AppException(ErrorCode.DIETARY_NOT_FOUND));
        UserDietary userDietary = userDietaryRepo.findByUserAndDietary(users, dietary)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DIETARY_NOT_FOUND));
        userDietaryRepo.delete(userDietary);
    }

    public List<UserDietaryResponse> viewDietariesByUser(String userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        return userDietaryRepo.findByUser(users).stream().map(userDietaryMapper::toUserDietaryResponse).collect(Collectors.toList());
    }
}
