package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionRepo extends JpaRepository<Nutrition, String> {
}
