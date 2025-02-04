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
    UNAUTHENTICATED(1009, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1010, "You don't have permission", HttpStatus.UNAUTHORIZED),
    ACCOUNT_VERIFIED(1011, "Tài khoản của bạn đã được xác thực", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT(1012, "Mật khẩu phải có ít nhất 6 ký tự, bao gồm ít nhất một chữ cái và một số", HttpStatus.BAD_REQUEST),
    //FIREBASE
    FILE_NOT_FOUND(1050, "Hình ảnh không tồn tại trên firebase", HttpStatus.NOT_FOUND),
    FILE_UPLOAD_FAILED(1151, "Lỗi khi tải hình ảnh lên firebase", HttpStatus.INTERNAL_SERVER_ERROR),

    //PREMIUM
    PREMIUM_NOT_REGISTERED(1100, "Bạn chưa đăng ký Premium", HttpStatus.BAD_REQUEST),
    PREMIUM_EXPIRED(1101, "Thời gian Premium của bạn đã hết hạn", HttpStatus.BAD_REQUEST),

    //ALLERGY
    ALLERGY_NOT_FOUND(1110, "Không tìm thấy thành phần dị ứng", HttpStatus.NOT_FOUND),
    FOOD_ALLERGY_NOT_FOUND(1116, "Không tìm thấy thành phần dị ứng đã được thêm vào món ăn", HttpStatus.NOT_FOUND),
    FOOD_ALLERGY_IS_EXISTED(1110, "Thành phần dị ứng đã được đêm vào món ăn", HttpStatus.BAD_REQUEST),
    USER_ALLERGY_NOT_FOUND(1116, "Không tìm thấy thành phần dị ứng của người dùng", HttpStatus.NOT_FOUND),
    USER_ALLERGY_IS_EXISTED(1110, "Thành phần dị ứng đã được thêm vào thông tin của người dùng", HttpStatus.BAD_REQUEST),

    //DIETARY
    DIETARY_NOT_FOUND(1111, "Không tìm thấy chế độ ăn", HttpStatus.NOT_FOUND),
    FOOD_DIETARY_NOT_FOUND(1111, "Không tìm thấy chế độ ăn của món ăn", HttpStatus.NOT_FOUND),
    FOOD_DIETARY_IS_EXISTED(1111, "Chế độ ăn đã được thêm vào món ăn", HttpStatus.BAD_REQUEST),
    USER_DIETARY_NOT_FOUND(1111, "Không tìm thấy chế độ ăn của người dùng", HttpStatus.NOT_FOUND),
    USER_DIETARY_IS_EXISTED(1111, "Chế độ ăn đã được thêm vào thông tin người dùng", HttpStatus.BAD_REQUEST),


    //METHOD COOKKING
    METHOD_COOKING_NOT_FOUND(1112, " Không tìm thấy phương pháp chế biến", HttpStatus.NOT_FOUND),
    FOOD_COOKING_METHOD_EXISTED(1112, "Phương thúc chế biến đã được thêm vào món ăn", HttpStatus.BAD_REQUEST),

    //FOOD
    FOOD_NOT_FOUND(1113, "Không tìm thấy thông tin món ăn", HttpStatus.NOT_FOUND),

    //STEP
    STEP_NOT_FOUND(1114, "Không tìm thấy bước chế biến món ăn", HttpStatus.NOT_FOUND),

    //NUTRITION
    NUTRITION_NOT_FOUND(1115, "Không tìm thấy số giá trị dinh dưỡng của món ăn", HttpStatus.NOT_FOUND),

    //INGREDIENT
    INGREDIENT_NOT_FOUND(1116, "Không tìm thấy nguyên liệu", HttpStatus.NOT_FOUND),

    //FAVORITE
    FAVORITE_IS_EXISTED(1119, "Món ăn đã được thêm vào mục yêu thích", HttpStatus.BAD_REQUEST),
    FAVORITE_NOT_FOUND(1120, "Không tìm thấy món ăn trong mục yêu thích", HttpStatus.NOT_FOUND),
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
