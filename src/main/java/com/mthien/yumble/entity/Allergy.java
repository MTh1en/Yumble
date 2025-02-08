package com.mthien.yumble.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", unique = true, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @OneToMany(mappedBy = "allergy")
    @JsonManagedReference
    private Set<FoodAllergy> foodAllergies;

    @OneToMany(mappedBy = "allergy")
    @JsonManagedReference
    private Set<UserAllergy> userAllergies;
}
