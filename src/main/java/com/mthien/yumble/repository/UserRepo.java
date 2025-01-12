package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepo extends JpaRepository<Users, String> {
    Optional<Users> findByEmail(String email);

    @Query("SELECT u.allergies FROM Users u WHERE  u.id= :id")
    Set<Allergy> findAllergiesById(String id);

    @Query("SELECT u.dietaries FROM Users u WHERE  u.id= :id")
    Set<Allergy> findDietariesById(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_allergy WHERE user_id= :userId", nativeQuery = true)
    void deleteAllergiesByUserId(String userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_dietary WHERE user_id= :userId", nativeQuery = true)
    void deleteDietariesByUserId(String userId);
}
