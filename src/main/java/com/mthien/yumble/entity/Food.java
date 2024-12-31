package com.mthien.yumble.entity;

import com.mthien.yumble.entity.Enum.Meal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Set<Allergy> allergies;

    @ManyToMany
    @JoinTable(
            name = "food_dietary",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "dietary_id")
    )
    private Set<Dietary> dietaries;

    @ManyToMany
    @JoinTable(
            name = "food_cooking_method",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns =  @JoinColumn(name = "method_cooking_id")
    )
    private Set<MethodCooking> methodCooking;
}
