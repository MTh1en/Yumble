package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodDietary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodDietaryRepo extends JpaRepository<FoodDietary, String> {
    Optional<FoodDietary> findByFoodAndDietary(Food food, Dietary dietary);
}
