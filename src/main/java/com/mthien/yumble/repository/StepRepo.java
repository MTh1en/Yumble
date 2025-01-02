package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface StepRepo extends JpaRepository<Step, String> {
    Set<Step> findByFoodIdOrderByStepOrder(String foodId);
}
