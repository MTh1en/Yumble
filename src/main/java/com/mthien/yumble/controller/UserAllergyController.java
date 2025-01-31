package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.dietary.AddUserAllergyRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyDetailResponse;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyResponse;
import com.mthien.yumble.service.UserAllergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserAllergyController {
    private final UserAllergyService userAllergyService;

    public UserAllergyController(UserAllergyService userAllergyService) {
        this.userAllergyService = userAllergyService;
    }

    @PostMapping("/{userId}/allergies")
    public ResponseEntity<ApiResponse<UserAllergyDetailResponse>> addUserAllergy(@PathVariable("userId") String userId,
                                                                                 @RequestBody AddUserAllergyRequest request) {
        var data = userAllergyService.addUserAllergy(userId, request);
        return ResponseEntity.ok(ApiResponse.<UserAllergyDetailResponse>builder()
                .code(200)
                .message("Đã thêm thành phần dị ứng vào thông tin của người dùng")
                .data(data)
                .build());
    }

    @DeleteMapping("/{userId}/allergies/{allergyId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserAllergy(@PathVariable("userId") String userId,
                                                               @PathVariable("allergyId") String allergyId) {
        userAllergyService.deleteUserAllergy(userId, allergyId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa thành phần dị ứng khỏi thông tin người dùng")
                .build());
    }

    @GetMapping("/{userId}/allergies")
    public ResponseEntity<ApiResponse<List<UserAllergyResponse>>> viewAllergiesByUser(@PathVariable("userId") String userId) {
        var data = userAllergyService.viewAllergiesByUser(userId);
        return ResponseEntity.ok(ApiResponse.<List<UserAllergyResponse>>builder()
                .code(200)
                .message("Đã thêm thành phần dị ứng vào thông tin của người dùng")
                .data(data)
                .build());
    }
}
