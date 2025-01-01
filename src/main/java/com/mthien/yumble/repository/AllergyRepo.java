package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AllergyRepo extends JpaRepository<Allergy, String> {
    Set<Allergy> findAllByNameIn(Set<String> allergyName);
}
