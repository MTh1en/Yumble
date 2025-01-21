package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NutritionRepo extends JpaRepository<Nutrition, String> {
    Optional<Nutrition> findByFood(Food food);
}
