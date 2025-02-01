package com.mthien.yumble.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    @JsonBackReference
    private Food food;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String ingredientName;

    @Column(name = "usage", nullable = false)
    private Float usage;

    @Column(name = "is_Core", nullable = false)
    private Boolean isCore;
}
