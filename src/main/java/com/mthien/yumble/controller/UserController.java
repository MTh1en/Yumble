package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.ChangePasswordRequest;
import com.mthien.yumble.payload.request.user.UpdateProfileRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.user.UserResponse;
import com.mthien.yumble.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PutMapping("/{userId}/profile")
    public ApiResponse<UserResponse> updateProfile(@PathVariable("userId") String userId,
                                                   @RequestBody UpdateProfileRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message(("Cập nhật thông tin tài khoản thành công"))
                .data(userService.updateProfile(userId, request))
                .build();
    }

    @PutMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserResponse> updateAvatar(@PathVariable("userId") String userId,
                                                  @RequestParam("file") MultipartFile file) {
        return ApiResponse.<UserResponse>builder()
                .message("Cập nhật hình đại diện của tài khoản thành công")
                .data(userService.updateAvatar(userId, file))
                .build();
    }

    @GetMapping("/{userId}/profile")
    public ApiResponse<UserResponse> viewProfile(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .message("Thông tin tài khoản của bạn")
                .data(userService.viewProfile(userId))
                .build();
    }

    @PutMapping("/{userId}/password")
    public ApiResponse<UserResponse> changePassword(@Valid @PathVariable("userId") String userId,
                                                    @RequestBody ChangePasswordRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Đổi mật khẩu thành công")
                .data(userService.changePassword(userId, request))
                .build();

    }

    //ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ApiResponse<List<UserResponse>> viewAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .message("Thông tin tất cả tài khoản trên hệ thống")
                .data(userService.getAllUsers())
                .build();
    }
}
