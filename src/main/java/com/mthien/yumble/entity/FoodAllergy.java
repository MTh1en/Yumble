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
public class FoodAllergy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    @JsonBackReference
    private Food food;

    @ManyToOne
    @JoinColumn(name = "allergy_id")
    @JsonBackReference
    private Allergy allergy;

    @Column(name = "severity", columnDefinition = "NVARCHAR(255)")
    private String severity;
}
