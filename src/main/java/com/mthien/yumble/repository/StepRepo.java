package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepo extends JpaRepository<Step, String> {
}
