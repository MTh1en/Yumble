package com.mthien.yumble.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mthien.yumble.entity.Enum.Meal;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "image")
    private String image;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @Column(name = "meal")
    @Enumerated(EnumType.STRING)
    private Meal meal;

    @Column(name = "cooking_method", columnDefinition = "NVARCHAR(255)")
    private String cookingMethod;

    @OneToOne(mappedBy = "food", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Nutrition nutrition;

    @OneToMany(mappedBy = "food",fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Step> steps;

    @OneToMany(mappedBy = "food")
    @JsonManagedReference
    private Set<Ingredient> ingredients;

    @OneToMany(mappedBy = "food")
    @JsonManagedReference
    private Set<FoodAllergy> foodAllergies;

    @OneToMany(mappedBy = "food")
    @JsonManagedReference
    private Set<FoodDietary> foodDietaries;

    @OneToMany(mappedBy = "food")
    @JsonManagedReference
    private Set<Favorite> favorites;
}
