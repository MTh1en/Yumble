package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.favorite.AddFavoriteRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.favorite.FavoriteResponse;
import com.mthien.yumble.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{userId}/foods")
    public ApiResponse<FavoriteResponse> addFavorite(@PathVariable("userId") String userId,
                                                     @RequestBody AddFavoriteRequest request) {
        return ApiResponse.<FavoriteResponse>builder()
                .message("Đã thêm món ăn vào mục yêu thích")
                .data(favoriteService.addFavorite(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}/foods/{foodId}")
    public ApiResponse<Void> deleteFavorite(@PathVariable("userId") String userId,
                                            @PathVariable("foodId") String foodId) {
        favoriteService.deleteFavorite(userId, foodId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa món ăn khỏi mục yêu thích")
                .build();
    }
}
