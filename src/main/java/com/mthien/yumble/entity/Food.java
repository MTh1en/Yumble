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

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "meal")
    @Enumerated(EnumType.STRING)
    private Meal meal;

    @OneToOne(mappedBy = "food")
    private Nutrition nutrition;

    @OneToMany(mappedBy = "food")
    private Set<Step> steps;

    @OneToMany(mappedBy = "food")
    private Set<IngredientUsing> ingredientUsings;

    @ManyToMany
    @JoinTable(
            name = "food_allergy",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "allergy_id")
    )
    @JsonManagedReference
    private Set<Allergy> allergies;

    @ManyToMany
    @JoinTable(
            name = "food_dietary",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "dietary_id")
    )
    @JsonManagedReference
    private Set<Dietary> dietaries;

    @ManyToMany
    @JoinTable(
            name = "food_cooking_method",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns =  @JoinColumn(name = "method_cooking_id")
    )
    @JsonManagedReference
    private Set<MethodCooking> methodCooking;
}
