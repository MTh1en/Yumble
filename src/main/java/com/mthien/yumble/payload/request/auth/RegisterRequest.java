package com.mthien.yumble.payload.request.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {
    @NotBlank(message = "Tên tài khoản không thể bỏ trống")
    private String name;

    @NotBlank(message = "Địa chỉ email không thể bỏ trống")
    @Email(message = "Định dạng email chưa hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không thể bỏ trống")
    @Pattern(
            regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            message = "Mật khẩu phải chứa ít nhất 8 ký tự, ít nhất 1 số, 1 chữ thường, 1 chữ hoa và 1 ký tự đặc biệt"
    )
    private String password;

}
