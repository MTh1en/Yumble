package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.MethodCooking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FoodRepo extends JpaRepository<Food, String> {
    @Query("SELECT DISTINCT f.allergies FROM Food  f WHERE f.id = :foodId")
    Set<Allergy> findAllergiesByFoodId(String foodId);

    @Query("SELECT DISTINCT f.dietaries FROM Food f WHERE f.id = :foodId")
    Set<Dietary> findDietaryByFoodId(String foodId);

    @Query("SELECT DISTINCT f.methodCooking from Food f where f.id= :foodId")
    Set<MethodCooking> findMethodCookingByFoodId(String foodId);

    @Query("SELECT DISTINCT f from Food  f")
    Set<Food> findAllFood();

    @Modifying
    //Khai báo cho JPA biết đây là phương thức sửa đội dữ liệu trong database
    // không phải phương thức mặc định query trả về kết quả củ JPA
    @Transactional
    //Khai báo câu query sẽ được thực hiện trong 1 Transactional, nếu có bug xảy ra sẽ roll back lại không thực hiện query nữa
    @Query(value = "DELETE FROM food_allergy WHERE food_id = :foodId", nativeQuery = true)
    void deleteAllergiesByFoodId(String foodId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM food_dietary WHERE food_id= :foodId", nativeQuery = true)
    void deleteDietaryByFoodId(String foodId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM food_cooking_method WHERE food_id= :foodId", nativeQuery = true)
    void deleteMethodCookingByFoodId(String foodId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO food_allergy (food_id, allergy_id) SELECT :foodId, a.id FROM allergy a WHERE a.name IN (:allergies)", nativeQuery = true)
    void saveAllergies(String foodId, String allergies);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO food_dietary (food_id, dietary_id) SELECT :foodId, d.id FROM dietary d WHERE d.name IN (:dietaries)", nativeQuery = true)
    void saveDietaries(String foodId, Set<String> dietaries);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO food_cooking_method (food_id, method_cooking_id) SELECT :foodId, m.id FROM method_cooking m WHERE m.name IN (:methodCookings)", nativeQuery = true)
    void saveMethodCookings(String foodId, Set<String> methodCookings);

}