package com.mthien.yumble.exception;

import com.mthien.yumble.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal Server Error")
                        .data(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppException(AppException ex) {
        return ResponseEntity.status(ex.getErrorCode().getStatusCode()).body(ApiResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .data(null)
                .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation Error")
                .data(errors)
                .build());
    }

}
