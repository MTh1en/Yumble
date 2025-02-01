package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.auth.LoginRequest;
import com.mthien.yumble.payload.request.auth.RegisterRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.auth.AuthResponse;
import com.mthien.yumble.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ApiResponse.<String>builder()
                .message("Đăng ký tài khoản thành công")
                .build();
    }

    @PostMapping("login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .message("Đăng nhập thành công")
                .data(authService.login(request))
                .build();
    }

    @PostMapping("send-verification-email/{email}")
    public ApiResponse<String> sendVerificationEmail(@PathVariable("email") String email) {
        authService.sendVerificationEmail(email);
        return ApiResponse.<String>builder()
                .message("Mail xác thực tài khoản đã được gửi đến email: " + email + "Vui lòng vào email của bạn để xác nhận")
                .build();
    }

    @GetMapping("verify")
    public ModelAndView verify(@RequestParam("token") String token) {
        authService.verifyAccount(token);
        return new ModelAndView("VerifySuccess");
    }
}
