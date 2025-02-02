package com.mthien.yumble.dataseed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodSeed {
    private String name;
    private String image;
    private String description;
    private String meal;
    private String cookingMethod;
    private List<IngredientSeed> ingredients;
    private List<StepSeed> steps;
    private NutritionSeed nutrition;
    private List<String> allergies;
    private List<String> dietaries;
}