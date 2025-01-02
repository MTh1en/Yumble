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
    INVALID_RE_PASSWORD(1007, "Mật khẩu nhập lần 2 không gioống mật khẩu mới", HttpStatus.BAD_REQUEST),
    DUPLICATE_PASSWORD(1008, "Mật khẩu mới và mật khẩu cũ không được trùng nhau", HttpStatus.BAD_REQUEST),

    //FIREBASE
    FILE_NOT_FOUND(1050, "Hình ảnh không tồn tại trên firebase", HttpStatus.NOT_FOUND),

    //PREMIUM
    PREMIUM_NOT_REGISTERED(1100, "Bạn chưa đăng ký Premium", HttpStatus.BAD_REQUEST),
    PREMIUM_EXPIRED(1101, "Thời gian Premium của bạn đã hết hạn", HttpStatus.BAD_REQUEST),

    //ALLERGY
    ALLERGY_NOT_FOUND(1110, "Không tìm thấy thành phần dị ứng", HttpStatus.NOT_FOUND),

    //DIETARY
    DIETARY_NOT_FOUND(1111, "Không tìm thấy chế độ ăn", HttpStatus.NOT_FOUND),

    //METHOD COOKKING
    METHOD_COOKING_NOT_FOUND(1112," Không tìm thấy phương pháp chế biến", HttpStatus.NOT_FOUND),

    //FOOD
    FOOD_NOT_FOUND(1113, "Không tìm thấy thông tin món ăn", HttpStatus.NOT_FOUND),

    //STEP
    STEP_NOT_FOUND(1114, "Không tìm thấy bước chế biến món ăn", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
