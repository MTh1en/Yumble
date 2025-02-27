package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.allergy.AddUserDietaryRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryDetailResponse;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryResponse;
import com.mthien.yumble.service.UserDietaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserDietaryController {
    private final UserDietaryService userDietaryService;

    @CacheEvict(value = "foods", allEntries = true)
    @PostMapping("/{userId}/dietaries")
    public ApiResponse<UserDietaryDetailResponse> addUserDietary(@PathVariable("userId") String userId,
                                                                 @RequestBody AddUserDietaryRequest request) {
        return ApiResponse.<UserDietaryDetailResponse>builder()
                .message("Đã thêm chế độ ăn uống vào thông tin người dùng")
                .data(userDietaryService.addUserDietary(userId, request))
                .build();
    }

    @CacheEvict(value = "foods", allEntries = true)
    @DeleteMapping("/{userId}/dietaries/{dietaryId}")
    public ApiResponse<Void> deleteUserDietary(@PathVariable("userId") String userId,
                                               @PathVariable("dietaryId") String dietaryId) {
        userDietaryService.deleteUserDietary(userId, dietaryId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa chế độ ăn uống khỏi thông tin người dùng")
                .build();
    }

    @GetMapping("/{userId}/dietaries")
    public ApiResponse<List<UserDietaryResponse>> viewDietariesByUser(@PathVariable("userId") String userId) {
        return ApiResponse.<List<UserDietaryResponse>>builder()
                .message("Thông tin chế độ ăn của người dùng")
                .data(userDietaryService.viewDietariesByUser(userId))
                .build();
    }
}
