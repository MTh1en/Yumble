package com.mthien.yumble.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //AUTH
    EMAIL_EXISTED(1001, "Email đã được đăng ký", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTED(1002, "Email chưa đươc đăng ký", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(1003, "Mật khẩu không đúng, vui lòng nhập lại", HttpStatus.FORBIDDEN),
    ACCOUNT_NOT_FOUND(1004, "Không tìm thấy tài khoản", HttpStatus.NOT_FOUND),
    INVALID_TOKEN(1005, "Token không hợp lệ", HttpStatus.FORBIDDEN),
    ACCOUNT_NOT_VERIFIED(1006, "Tài khoản của bạn chưa được xác thực", HttpStatus.FORBIDDEN),

    //FIREBASE
    FILE_NOT_FOUND(1050, "Hình ảnh không tồn tại trên firebase", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
