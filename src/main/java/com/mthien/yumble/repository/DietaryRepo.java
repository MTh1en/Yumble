package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Dietary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietaryRepo extends JpaRepository<Dietary, String> {
    Dietary findByName(String name);
}
