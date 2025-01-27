package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.favorite.AddFavoriteRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.favorite.FavoriteResponse;
import com.mthien.yumble.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("users/{userId}/foods")
    public ResponseEntity<ApiResponse<FavoriteResponse>> addFavorite(@PathVariable("userId") String userId,
                                                                     @RequestBody AddFavoriteRequest request) {
        var data = favoriteService.addFavorite(userId, request);
        return ResponseEntity.ok(ApiResponse.<FavoriteResponse>builder()
                .code(200)
                .message("Đã thêm món ăn vào mục yêu thích")
                .data(data)
                .build());
    }

    @DeleteMapping("users/{userId}/foods/{foodId}")
    public ResponseEntity<ApiResponse<Void>> deleteFavorite(@PathVariable("userId") String userId,
                                                            @PathVariable("foodId") String foodId) {
        favoriteService.deleteFavorite(userId, foodId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa món ăn khỏi mục yêu thích")
                .build());
    }
}
