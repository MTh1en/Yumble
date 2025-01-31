package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.FoodAllergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodAllergyRepo extends JpaRepository<FoodAllergy, String> {
    Optional<FoodAllergy> findByAllergyAndFood(Allergy allergy, Food food);

    List<FoodAllergy> findByFood(Food food);
}
