package com.mthien.yumble.repository;

import com.mthien.yumble.entity.CookingMethod;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodCookingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodCookingMethodRepo extends JpaRepository<FoodCookingMethod, String> {
    Optional<FoodCookingMethod> findByFoodAndCookingMethod(Food food, CookingMethod cookingMethod);

    List<FoodCookingMethod> findByFood(Food food);
}
