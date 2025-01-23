package com.mthien.yumble.repository;

import com.mthien.yumble.entity.CookingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface CookingMethodRepo extends JpaRepository<CookingMethod, String> {
    Set<CookingMethod> findAllByNameIn(Set<String> names);
}
