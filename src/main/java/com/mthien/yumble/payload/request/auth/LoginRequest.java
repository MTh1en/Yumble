package com.mthien.yumble.payload.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Địa chỉ email không thể bỏ trống")
    @Email(message = "Định dạng email chưa hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không thể bỏ trống")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Mật khẩu phải có ít nhất 6 ký tự, có ít nhất 1 số, có ít nhất 1 chữ cái"
    )
    private String password;
}