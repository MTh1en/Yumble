package com.mthien.yumble.repository;

import com.mthien.yumble.entity.IngredientUsing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientUsingRepo extends JpaRepository<IngredientUsing, String> {
}
