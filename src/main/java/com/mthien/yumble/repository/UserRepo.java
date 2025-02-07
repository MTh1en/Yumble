package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, String> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByName(String name);
}
