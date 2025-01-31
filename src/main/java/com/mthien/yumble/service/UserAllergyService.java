package com.mthien.yumble.service;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.UserAllergy;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.UserAllergyMapper;
import com.mthien.yumble.payload.request.user.dietary.AddUserAllergyRequest;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyDetailResponse;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyResponse;
import com.mthien.yumble.repository.AllergyRepo;
import com.mthien.yumble.repository.UserAllergyRepo;
import com.mthien.yumble.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAllergyService {
    private final UserAllergyMapper userAllergyMapper;
    private final UserAllergyRepo userAllergyRepo;
    private final UserRepo userRepo;
    private final AllergyRepo allergyRepo;

    public UserAllergyService(UserAllergyMapper userAllergyMapper, UserAllergyRepo userAllergyRepo, UserRepo userRepo, AllergyRepo allergyRepo) {
        this.userAllergyMapper = userAllergyMapper;
        this.userAllergyRepo = userAllergyRepo;
        this.userRepo = userRepo;
        this.allergyRepo = allergyRepo;
    }

    public UserAllergyDetailResponse addUserAllergy(String userId, AddUserAllergyRequest request) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Allergy allergy = allergyRepo.findById(request.getAllergyId())
                .orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        userAllergyRepo.findByUserAndAllergy(users, allergy).ifPresent(existingUserAllergy -> {
            throw new AppException(ErrorCode.USER_ALLERGY_IS_EXISTED);
        });
        UserAllergy userAllergy = userAllergyMapper.addUserAllergy(users, allergy, request);
        return userAllergyMapper.toUserAllergyDetailResponse(userAllergyRepo.save(userAllergy));
    }

    public void deleteUserAllergy(String userId, String allergyId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Allergy allergy = allergyRepo.findById(allergyId)
                .orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        UserAllergy userAllergy = userAllergyRepo.findByUserAndAllergy(users, allergy)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ALLERGY_NOT_FOUND));
        userAllergyRepo.delete(userAllergy);
    }

    public List<UserAllergyResponse> viewAllergiesByUser(String userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        return userAllergyRepo.findByUser(users).stream().map(userAllergyMapper::toUserAllergyResponse).collect(Collectors.toList());
    }
}
