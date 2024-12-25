package com.mthien.yumble.payload.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotBlank(message = "Mật khẩu không thể bỏ trống")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Mật khẩu phải có ít nhất 6 ký tự, có ít nhất 1 số, có ít nhất 1 chữ cái"
    )
    private String oldPassword;

    @NotBlank(message = "Mật khẩu không thể bỏ trống")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Mật khẩu phải có ít nhất 6 ký tự, có ít nhất 1 số, có ít nhất 1 chữ cái"
    )
    private String newPassword;

    @NotBlank(message = "Mật khẩu không thể bỏ trống")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Mật khẩu phải có ít nhất 6 ký tự, có ít nhất 1 số, có ít nhất 1 chữ cái"
    )
    private String confirmPassword;
}
