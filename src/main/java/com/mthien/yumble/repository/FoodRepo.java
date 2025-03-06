package com.mthien.yumble.repository;

import com.mthien.yumble.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepo extends JpaRepository<Food, String> {
    Food findByNameIgnoreCase(String name);

    @Query("""
    SELECT DISTINCT f FROM Food f
    LEFT JOIN f.foodAllergies fa
    LEFT JOIN f.foodDietaries fd
    WHERE (:userAllergyIds IS NULL OR NOT EXISTS (
        SELECT 1 FROM f.foodAllergies fa2 WHERE fa2.allergy.id IN :userAllergyIds
    ))
    AND (:userDietaryIds IS NULL OR EXISTS (
        SELECT 1 FROM f.foodDietaries fd2 WHERE fd2.dietary.id IN :userDietaryIds
    ))
""")
    Page<Food> findRecommendedFoods(
            @Param("userAllergyIds") List<String> userAllergyIds,
            @Param("userDietaryIds") List<String> userDietaryIds,
            Pageable pageable
    );

    @Query("SELECT DISTINCT f.name FROM Food f")
    List<String> findFoodNameForAI();
}