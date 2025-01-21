package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepo extends JpaRepository<Food, String> {

}