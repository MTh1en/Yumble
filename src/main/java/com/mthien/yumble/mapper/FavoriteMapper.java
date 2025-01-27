package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Favorite;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.payload.response.favorite.FavoriteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "food", expression = "java(food)")
    Favorite addFavorite(Users user, Food food);

    FavoriteResponse toFavoriteResponse(Favorite favorite);
}
