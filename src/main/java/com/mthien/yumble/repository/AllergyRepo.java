package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepo extends JpaRepository<Allergy, String> {
}
