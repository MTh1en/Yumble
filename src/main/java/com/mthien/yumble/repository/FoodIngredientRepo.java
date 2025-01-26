package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Ingredient;
import com.mthien.yumble.entity.FoodIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodIngredientRepo extends JpaRepository<FoodIngredient, String> {
    Optional<FoodIngredient> findByFoodAndIngredient(Food food, Ingredient ingredient);
}
