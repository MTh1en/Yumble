package com.mthien.yumble.payload.response.favorite;

import com.mthien.yumble.payload.response.food.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFavoriteResponse {
    private String id;
    private FoodResponse food;
}
