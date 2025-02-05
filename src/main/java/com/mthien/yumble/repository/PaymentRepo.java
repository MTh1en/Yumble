package com.mthien.yumble.repository;

import com.mthien.yumble.entity.Enum.PaymentStatus;
import com.mthien.yumble.entity.Payment;
import com.mthien.yumble.entity.Premium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment, String> {
    Optional<Payment> findByPremiumAndStatusOrderByTimeDesc(Premium premium, PaymentStatus paymentStatus);

    List<Payment> findByPremiumAndStatus(Premium premium, PaymentStatus status);
}
