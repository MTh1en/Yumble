package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.user.allergy.AddUserDietaryRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryDetailResponse;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryResponse;
import com.mthien.yumble.service.UserDietaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserDietaryController {
    private final UserDietaryService userDietaryService;

    public UserDietaryController(UserDietaryService userDietaryService) {
        this.userDietaryService = userDietaryService;
    }

    @PostMapping("/{userId}/dietaries")
    public ResponseEntity<ApiResponse<UserDietaryDetailResponse>> addUserDietary(@PathVariable("userId") String userId,
                                                                                 @RequestBody AddUserDietaryRequest request) {
        var data = userDietaryService.addUserDietary(userId, request);
        return ResponseEntity.ok(ApiResponse.<UserDietaryDetailResponse>builder()
                .code(200)
                .message("Đã thêm chế độ ăn uống vào thông tin người dùng")
                .data(data)
                .build());
    }

    @DeleteMapping("/{userId}/dietaries/{dietaryId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserDietary(@PathVariable("userId") String userId,
                                                               @PathVariable("dietaryId") String dietaryId) {
        userDietaryService.deleteUserDietary(userId, dietaryId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa chế độ ăn uống khỏi thông tin người dùng")
                .build());
    }

    @GetMapping("/{userId}/dietaries")
    public ResponseEntity<ApiResponse<List<UserDietaryResponse>>> viewDietariesByUser(@PathVariable("userId") String userId) {
        var data = userDietaryService.viewDietariesByUser(userId);
        return ResponseEntity.ok(ApiResponse.<List<UserDietaryResponse>>builder()
                .code(200)
                .message("Thông tin chế độ ăn của người dùng")
                .data(data)
                .build());
    }
}
