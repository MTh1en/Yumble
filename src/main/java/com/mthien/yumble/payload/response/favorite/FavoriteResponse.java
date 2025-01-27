package com.mthien.yumble.payload.response.favorite;

import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.payload.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteResponse {
    private String id;
    private FoodResponse food;
    private UserResponse user;
}
