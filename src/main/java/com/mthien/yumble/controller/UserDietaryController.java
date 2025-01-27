package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.allergy.AddUserDietaryRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryResponse;
import com.mthien.yumble.service.UserDietaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserDietaryController {
    private final UserDietaryService userDietaryService;

    public UserDietaryController(UserDietaryService userDietaryService) {
        this.userDietaryService = userDietaryService;
    }

    @PostMapping("users/{userId}/dietaries")
    public ResponseEntity<ApiResponse<UserDietaryResponse>> addUserDietary(@PathVariable("userId") String userId,
                                                                           @RequestBody AddUserDietaryRequest request) {
        var data = userDietaryService.addUserDietary(userId, request);
        return ResponseEntity.ok(ApiResponse.<UserDietaryResponse>builder()
                .code(200)
                .message("Đã thêm chế độ ăn uống vào thông tin người dùng")
                .data(data)
                .build());
    }

    @DeleteMapping("users/{userId}/dietaries/{dietaryId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserDietary(@PathVariable("userId") String userId,
                                                               @PathVariable("dietaryId") String dietaryId) {
        userDietaryService.deleteUserDietary(userId, dietaryId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa chế độ ăn uống khỏi thông tin người dùng")
                .build());
    }
}
