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
        SELECT f FROM Food f 
        LEFT JOIN f.foodAllergies fa 
        LEFT JOIN f.foodDietaries fd
        WHERE (:userAllergies IS NULL OR fa.allergy NOT IN :userAllergies)
          AND (:userDietaries IS NULL OR fd.dietary IN :userDietaries)
    """)
    List<Food> findRecommendedFoods(
            @Param("userAllergies") List<Allergy> userAllergies,
            @Param("userDietaries") List<Dietary> userDietaries
    );
}