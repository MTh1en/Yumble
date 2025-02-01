package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.dietary.AddUserAllergyRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyDetailResponse;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyResponse;
import com.mthien.yumble.service.UserAllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserAllergyController {
    private final UserAllergyService userAllergyService;

    @PostMapping("/{userId}/allergies")
    public ApiResponse<UserAllergyDetailResponse> addUserAllergy(@PathVariable("userId") String userId,
                                                                 @RequestBody AddUserAllergyRequest request) {
        return ApiResponse.<UserAllergyDetailResponse>builder()
                .message("Đã thêm thành phần dị ứng vào thông tin của người dùng")
                .data(userAllergyService.addUserAllergy(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}/allergies/{allergyId}")
    public ApiResponse<Void> deleteUserAllergy(@PathVariable("userId") String userId,
                                               @PathVariable("allergyId") String allergyId) {
        userAllergyService.deleteUserAllergy(userId, allergyId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa thành phần dị ứng khỏi thông tin người dùng")
                .build();
    }

    @GetMapping("/{userId}/allergies")
    public ApiResponse<List<UserAllergyResponse>> viewAllergiesByUser(@PathVariable("userId") String userId) {
        return ApiResponse.<List<UserAllergyResponse>>builder()
                .message("Đã thêm thành phần dị ứng vào thông tin của người dùng")
                .data(userAllergyService.viewAllergiesByUser(userId))
                .build();
    }
}
