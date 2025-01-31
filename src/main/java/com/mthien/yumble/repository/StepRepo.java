package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepo extends JpaRepository<Step, String> {
    List<Step> findByFoodIdOrderByStepOrder(String foodId);
}
