package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepo extends JpaRepository<Food, String> {
    Food findByNameIgnoreCase(String name);
}