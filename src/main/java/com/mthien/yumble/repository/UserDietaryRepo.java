package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.UserDietary;
import com.mthien.yumble.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDietaryRepo extends JpaRepository<UserDietary, String> {
    Optional<UserDietary> findByUserAndDietary(Users user, Dietary dietary);

    List<UserDietary> findByUser(Users user);
}
