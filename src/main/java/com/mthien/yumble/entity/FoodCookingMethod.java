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
public class FoodCookingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    @JsonBackReference
    private Food food;

    @ManyToOne
    @JoinColumn(name = "cooking_method_id")
    @JsonBackReference
    private CookingMethod cookingMethod;

    @Column(name = "time_required")
    private Float timeRequired;

    @Column(name = "is_Core", nullable = false)
    private Boolean isCore;
}
