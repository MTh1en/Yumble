package com.mthien.yumble.controller;

import com.mthien.yumble.exception.AppException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //AUTHENTICATE USER
    @PostMapping("send-verification-email/{email}")
    public ApiResponse<Void> sendVerificationEmail(@PathVariable("email") String email) {
        authService.sendVerificationEmail(email);
        return ApiResponse.<Void>builder()
                .message("Mail xác thực tài khoản đã được gửi đến email: " + email + " Vui lòng vào email của bạn để xác nhận")
                .build();
    }

    @GetMapping("verify")
    public ModelAndView verify(@RequestParam("token") String token) {
        try {
            authService.verifyAccount(token);
            authService.revokeToken(token);
            return new ModelAndView("VerifySuccess");
        } catch (AppException e) {
            return new ModelAndView("VerifyFail");
        }
    }

    //FORGOT PASSWORD
    @PostMapping("send-forgot-password-email/{email}")
    public ApiResponse<Void> sendForgotPasswordEmail(@PathVariable("email") String email) {
        authService.sendForgotPasswordEmail(email);
        return ApiResponse.<Void>builder()
                .message("Mail yêu của đặt lại mật khẩu của bạn đã được gửi đến email: " + email + "Vui lòng vào email để đặt lại mật khẩu")
                .build();
    }

    @GetMapping("reset-password")
    public ModelAndView forgotPassword(@RequestParam("token") String token) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("token", token);
        try {
            authService.verifiedToken(token, false);
            modelAndView.setViewName("ResetPassword");
        } catch (AppException e) {
            modelAndView.setViewName("UnauthorizedResetPassword");
        }
        return modelAndView;
    }

    @PostMapping("reset-password")
    public ModelAndView resetPassword(@RequestParam("token") String token,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            authService.resetPassword(token, newPassword, confirmPassword);
            modelAndView.setViewName("ResetPasswordSuccess");
            authService.revokeToken(token);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            modelAndView.setViewName("redirect:/auth/reset-password?token=" + token);
        }
        return modelAndView;
    }

    //VALID TOKEN
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .message("Token của bạn đã được kiểm duyệt")
                .data(authService.introspect(request))
                .build();
    }

    //LOGOUT
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request){
        authService.logout(request);
        return ApiResponse.<Void>builder()
                .message("Bạn đã đăng xuất thành công")
                .build();
    }

    //REFRESH TOKEN
    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthResponse>builder()
                .message("Bạn đã refresh token thành công")
                .data(authService.refreshToken(request))
                .build();
    }
}
