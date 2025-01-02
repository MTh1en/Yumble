package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.MethodCooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FoodRepo extends JpaRepository<Food, String> {
    @Query("SELECT DISTINCT f.allergies FROM Food f where f.id= :foodId")
    Set<Allergy> findAllergiesByFoodId(String foodId);

    @Query("SELECT DISTINCT f.dietaries FROM Food f WHERE f.id = :foodId")
    Set<Dietary> findDietaryByFoodId(String foodId);

    @Query("SELECT DISTINCT f.methodCooking from Food f where f.id= :foodId")
    Set<MethodCooking> findMethodCookingByFoodId(String foodId);
}