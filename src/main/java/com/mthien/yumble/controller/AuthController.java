package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.auth.LoginRequest;
import com.mthien.yumble.payload.request.auth.RegisterRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.auth.AuthResponse;
import com.mthien.yumble.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Đăng ký tài khoản thành công")
                .build());
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        var data = authService.login(request);
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .code(200)
                .message("Đăng nhập thành công")
                .data(data)
                .build());
    }

    @PostMapping("send-verification-email/{email}")
    public ResponseEntity<ApiResponse<String>> sendVerificationEmail(@PathVariable("email") String email) {
        authService.sendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Mail xác thực tài khoản đã được gửi đến email: " + email + "Vui lòng vào email của bạn để xác nhận")
                .build());
    }

    @GetMapping("verify")
    public ModelAndView verify(@RequestParam("token") String token) {
        authService.verifyAccount(token);
        return new ModelAndView("VerifySuccess");
    }
}
