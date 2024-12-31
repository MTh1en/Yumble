package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepo extends JpaRepository<Ingredient, String> {
}
