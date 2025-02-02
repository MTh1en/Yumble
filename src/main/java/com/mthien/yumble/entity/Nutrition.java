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
@Entity
@Builder
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "calories", nullable = false)
    private Float calories;

    @Column(name = "protein", nullable = false)
    private Float protein;

    @Column(name = "fat", nullable = false)
    private Float fat;

    @Column(name = "carb", nullable = false)
    private Float carb;

    @Column(name = "fiber", nullable = false)
    private Float fiber;

    @Column(name = "sugar", nullable = false)
    private Float sugar;

    @Column(name = "sodium", nullable = false)
    private Float sodium;

    @Column(name = "cholesterol", nullable = false)
    private Float cholesterol;

    @OneToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    @JsonBackReference
    private Food food;

}
