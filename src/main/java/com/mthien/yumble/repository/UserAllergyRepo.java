package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.UserAllergy;
import com.mthien.yumble.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAllergyRepo extends JpaRepository<UserAllergy, String> {
    Optional<UserAllergy> findByUserAndAllergy(Users user, Allergy allergy);
}
