package com.mthien.yumble.service;

import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.UserMapper;
import com.mthien.yumble.payload.request.user.ChangePasswordRequest;
import com.mthien.yumble.payload.request.user.UpdateProfileRequest;
import com.mthien.yumble.payload.response.user.UserResponse;
import com.mthien.yumble.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final FirebaseService firebaseService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserResponse updateProfile(String id, UpdateProfileRequest request) {
        Users users = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        userMapper.updateProfile(users, request);
        return userMapper.toUserResponse(userRepo.save(users));
    }

    public UserResponse updateAvatar(String id, MultipartFile avatar) {
        Users users = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        users.setAvatar(firebaseService.uploadFile(generateUniqueAvatarFileName(users), avatar));
        return userMapper.toUserResponse(userRepo.save(users));

    }
    @PostAuthorize("returnObject.name == authentication.name")
    public UserResponse viewProfile(String id) {
        Users users = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        String avatar = Optional.ofNullable(users.getAvatar())
                .filter(avatarUrl -> avatarUrl.contains("avatar"))
                .map(firebaseService::getImageUrl)
                .orElse(users.getAvatar());
        return userMapper.toUserResponse(users, avatar);
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        log.info("In method getAllUser");
        return userRepo.findAll().stream().map(users1 -> {
            String avatar = Optional.ofNullable(users1.getAvatar())
                    .filter(avatarUrl -> avatarUrl.contains("avatar"))
                    .map(firebaseService::getImageUrl)
                    .orElse(users1.getAvatar());
            return userMapper.toUserResponse(users1, avatar);
        }).collect(Collectors.toList());
    }

    public String generateUniqueAvatarFileName(Users user) {
        String uniqueId = UUID.randomUUID().toString();
        return String.format("avatar/%s_%s", uniqueId, user.getEmail());
    }
}
