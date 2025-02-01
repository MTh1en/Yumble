package com.mthien.yumble.service;

import com.mthien.yumble.entity.Favorite;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.FavoriteMapper;
import com.mthien.yumble.payload.request.favorite.AddFavoriteRequest;
import com.mthien.yumble.payload.response.favorite.FavoriteResponse;
import com.mthien.yumble.repository.FavoriteRepo;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteMapper favoriteMapper;
    private final FavoriteRepo favoriteRepo;
    private final UserRepo userRepo;
    private final FoodRepo foodRepo;

    public FavoriteResponse addFavorite(String userId, AddFavoriteRequest request) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Food food = foodRepo.findById(request.getFoodId())
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        favoriteRepo.findByUserAndFood(user, food).ifPresent(existingFavorite -> {
            throw new AppException(ErrorCode.FAVORITE_IS_EXISTED);
        });
        Favorite favorite = favoriteMapper.addFavorite(user, food);
        return favoriteMapper.toFavoriteResponse(favoriteRepo.save(favorite));
    }

    public void deleteFavorite(String userId, String foodId) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Favorite favorite = favoriteRepo.findByUserAndFood(user, food)
                .orElseThrow(() -> new AppException(ErrorCode.FAVORITE_NOT_FOUND));
        favoriteRepo.delete(favorite);
    }
}
