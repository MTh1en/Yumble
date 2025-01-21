package com.mthien.yumble.repository;

import com.mthien.yumble.entity.MethodCooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface MethodCookingRepo extends JpaRepository<MethodCooking, String> {
    Set<MethodCooking> findAllByNameIn(Set<String> names);
}
