package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Dietary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface DietaryRepo extends JpaRepository<Dietary, String> {
    Set<Dietary> findAllByNameIn(Set<String> names);
}
