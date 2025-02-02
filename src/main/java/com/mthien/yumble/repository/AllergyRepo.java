package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepo extends JpaRepository<Allergy, String> {
    Allergy findByName(String name);
}
