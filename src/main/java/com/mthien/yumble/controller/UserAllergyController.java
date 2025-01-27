package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.dietary.AddUserAllergyRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyResponse;
import com.mthien.yumble.service.UserAllergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAllergyController {
    private final UserAllergyService userAllergyService;

    public UserAllergyController(UserAllergyService userAllergyService) {
        this.userAllergyService = userAllergyService;
    }

    @PostMapping("users/{userId}/allergies")
    public ResponseEntity<ApiResponse<UserAllergyResponse>> addUserAllergy(@PathVariable("userId") String userId,
                                                                           @RequestBody AddUserAllergyRequest request) {
        var data = userAllergyService.addUserAllergy(userId, request);
        return ResponseEntity.ok(ApiResponse.<UserAllergyResponse>builder()
                .code(200)
                .message("Đã thêm thành phần dị ứng vào thông tin của người dùng")
                .data(data)
                .build());
    }

    @DeleteMapping("users/{userId}/allergies/{allergyId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserAllergy(@PathVariable("userId") String userId,
                                                               @PathVariable("allergyId") String allergyId) {
        userAllergyService.deleteUserAllergy(userId, allergyId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa thành phần dị ứng khỏi thông tin người dùng")
                .build());
    }
}
