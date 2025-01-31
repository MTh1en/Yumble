package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.AddAllergiesAndDietariesRequest;
import com.mthien.yumble.payload.request.user.ChangePasswordRequest;
import com.mthien.yumble.payload.request.user.UpdateProfileRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.user.UserResponse;
import com.mthien.yumble.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@PathVariable("userId") String userId,
                                                                   @RequestBody UpdateProfileRequest request) {
        var data = userService.updateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message(("Cập nhật thông tin tài khoản thành công"))
                .data(data)
                .build());
    }

    @PutMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> updateAvatar(@PathVariable("userId") String userId,
                                                                  @RequestParam("file") MultipartFile file) throws IOException {
        var data = userService.updateAvatar(userId, file);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Cập nhật hình đại diện của tài khoản thành công")
                .data(data)
                .build());
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse<UserResponse>> viewProfile(@PathVariable("userId") String userId) {
        var data = userService.viewProfile(userId);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Thông tin tài khoản của bạn")
                .data(data)
                .build());
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<ApiResponse<UserResponse>> changePassword(@Valid @PathVariable("userId") String userId,
                                                                    @RequestBody ChangePasswordRequest request) {
        var data = userService.changePassword(userId, request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Đổi mật khẩu thành công")
                .data(data)
                .build());

    }
    //ADMIN.
    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserResponse>>> viewAll() {
        var data = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("Thông tin tất cả tài khoản trên hệ thống")
                .data(data)
                .build());
    }
}
