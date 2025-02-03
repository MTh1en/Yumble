package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.auth.*;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.auth.AuthResponse;
import com.mthien.yumble.payload.response.auth.IntrospectResponse;
import com.mthien.yumble.service.AuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

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

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .message("Token của bạn đã được kiểm duyệt")
                .data(authService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authService.logout(request);
        return ApiResponse.<Void>builder()
                .message("Bạn đã đăng xuất thành công")
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {

        return ApiResponse.<AuthResponse>builder()
                .message("Bạn đã refresh token thành công")
                .data(authService.refreshToken(request))
                .build();
    }

    @GetMapping("verify")
    public ModelAndView verify(@RequestParam("token") String token){
        authService.verifyAccount(token);
        return new ModelAndView("VerifySuccess");
    }
}
