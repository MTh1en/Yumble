package com.mthien.yumble.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserDietary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users user;

    @ManyToOne
    @JoinColumn(name = "dietary_id")
    @JsonBackReference
    private Dietary dietary;

    @Column(name = "priority", columnDefinition = "NVARCHAR(255)")
    private String priority;
}
