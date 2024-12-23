package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Premium;
import com.mthien.yumble.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PremiumRepo extends JpaRepository<Premium, String> {
    Optional<Premium> findByUsers(Users users);
}
