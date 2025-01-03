package com.mthien.yumble.service;

import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.UserMapper;
import com.mthien.yumble.payload.request.user.ChangePasswordRequest;
import com.mthien.yumble.payload.request.user.UpdateProfileRequest;
import com.mthien.yumble.payload.response.user.UserResponse;
import com.mthien.yumble.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final FirebaseService firebaseService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepo userRepo, UserMapper userMapper, FirebaseService firebaseService) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.firebaseService = firebaseService;
    }

    public UserResponse updateProfile(String id, UpdateProfileRequest request) {
        Users users = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        userMapper.updateProfile(users, request);
        return userMapper.toUserResponse(userRepo.save(users));
    }

    public UserResponse updateAvatar(String id, MultipartFile avatar) throws IOException {
        Users users = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        String imageUrl = firebaseService.uploadFile(users.getEmail(), avatar);
        users.setAvatar(imageUrl);
        return userMapper.toUserResponse(userRepo.save(users));

    }

    public UserResponse viewProfile(String id) {
        Users users = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        return userMapper.toUserResponse(users);
    }

    public UserResponse changePassword(String id, ChangePasswordRequest request) {
        Users users = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        if (!passwordEncoder.matches(request.getOldPassword(), users.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.INVALID_RE_PASSWORD);
        }
        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new AppException(ErrorCode.DUPLICATE_PASSWORD);
        }
        users.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return userMapper.toUserResponse(userRepo.save(users));
    }

    //ADMIN
    public List<UserResponse> getAllUsers() {
        List<Users> users = userRepo.findAll();
        return userMapper.toListUserResponse(users);
    }
}
