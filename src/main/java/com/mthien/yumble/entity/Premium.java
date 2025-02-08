package com.mthien.yumble.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mthien.yumble.entity.Enum.PremiumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Premium {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    @OneToMany(mappedBy = "premium")
    @JsonManagedReference
    private Set<Payment> payments;

    @Column(name = "start_time")
    private LocalDateTime start;

    @Column(name = "end_time")
    private LocalDateTime end;

    @Column(name = "remaining")
    private Long remaining;

    @Column(name = "premium_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PremiumStatus premiumStatus;
}
