package com.mthien.yumble.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mthien.yumble.entity.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "premium_id", nullable = false)
    @JsonBackReference
    private Premium premium;

    @Column(name = "code", unique = true, nullable = false)
    private Long code;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
