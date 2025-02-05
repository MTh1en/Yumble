package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Favorite;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepo extends JpaRepository<Favorite, String> {
    Optional<Favorite> findByUserAndFood(Users user, Food food);

    List<Favorite> findByUser(Users users);
}
