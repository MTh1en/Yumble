package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepo extends JpaRepository<Ingredient, String> {
    List<Ingredient> findByFoodOrderByIsCoreDesc(Food food);
}
