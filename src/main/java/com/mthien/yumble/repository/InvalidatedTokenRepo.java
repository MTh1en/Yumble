package com.mthien.yumble.repository;

import com.mthien.yumble.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepo extends JpaRepository<InvalidatedToken, String> {
}
