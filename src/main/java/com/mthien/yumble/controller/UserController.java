package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.UpdateProfileRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.user.UserResponse;
import com.mthien.yumble.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("update-profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@RequestParam("id") Integer id,
                                                                   @RequestBody UpdateProfileRequest request) {
        var data = userService.updateProfile(id, request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message(("Cập nhật thông tin tài khoản thành công"))
                .data(data)
                .build());
    }

    @PostMapping(value = "update-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> updateAvatar(@RequestParam("id") Integer id,
                                                                  @RequestParam("file") MultipartFile file) throws IOException {
        var data = userService.updateAvatar(id, file);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Cập nhật hình đại diện của tài khoản thành công")
                .data(data)
                .build());
    }
}
