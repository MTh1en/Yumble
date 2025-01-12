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
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("update-profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@PathVariable("id") String id,
                                                                   @RequestBody UpdateProfileRequest request) {
        var data = userService.updateProfile(id, request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message(("Cập nhật thông tin tài khoản thành công"))
                .data(data)
                .build());
    }

    @PostMapping(value = "update-avatar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> updateAvatar(@PathVariable("id") String id,
                                                                  @RequestParam("file") MultipartFile file) throws IOException {
        var data = userService.updateAvatar(id, file);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Cập nhật hình đại diện của tài khoản thành công")
                .data(data)
                .build());
    }

    @GetMapping("view-profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> viewProfile(@PathVariable("id") String id) {
        var data = userService.viewProfile(id);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Thông tin tài khoản của bạn")
                .data(data)
                .build());
    }

    @PutMapping("change-password/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> changePassword(@Valid @PathVariable("id") String id,
                                                                    @RequestBody ChangePasswordRequest request) {
        var data = userService.changePassword(id, request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Đổi mật khẩu thành công")
                .data(data)
                .build());

    }

    @PutMapping(value = "add-allergies-and-dietaries{id}")
    public ResponseEntity<ApiResponse<UserResponse>> addAllAllergiesAndDietaries(@PathVariable("id") String id,
                                                                                 @RequestBody AddAllergiesAndDietariesRequest request) {
        var data = userService.addAllergiesAndDietaries(id, request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Cập nhật thông tin ăn uống cá nhân thành công")
                .data(data)
                .build());
    }

    //ADMIN.
    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> viewAll() {
        var data = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("Thông tin tất cả tài khoản trên hệ thống")
                .data(data)
                .build());
    }
}
