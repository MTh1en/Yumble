package com.mthien.yumble.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mthien.yumble.dataseed.AllergyDietarySeed;
import com.mthien.yumble.dataseed.FoodSeed;
import com.mthien.yumble.entity.*;
import com.mthien.yumble.entity.Enum.Meal;
import com.mthien.yumble.entity.Enum.PremiumStatus;
import com.mthien.yumble.entity.Enum.Role;
import com.mthien.yumble.entity.Enum.UserStatus;
import com.mthien.yumble.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements ApplicationRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    private final DietaryRepo dietaryRepo;
    private final AllergyRepo allergyRepo;
    private final FoodRepo foodRepo;
    private final IngredientRepo ingredientRepo;
    private final StepRepo stepRepo;
    private final NutritionRepo nutritionRepo;
    private final FoodAllergyRepo foodAllergyRepo;
    private final FoodDietaryRepo foodDietaryRepo;
    private final ObjectMapper objectMapper;
    private final UserAllergyRepo userAllergyRepo;
    private final UserDietaryRepo userDietaryRepo;
    private final PremiumRepo premiumRepo;

    @Override
    public void run(ApplicationArguments args) {
        if (allergyRepo.count() == 0) {
            try {
                InputStream inputStream = getClass().getResourceAsStream("/allergies.json");
                List<AllergyDietarySeed> allergySeeds = objectMapper.readValue(inputStream, new TypeReference<>() {
                });
                allergySeeds.forEach(allergySeed -> {
                    Allergy allergy = Allergy.builder()
                            .name(allergySeed.getName())
                            .description(allergySeed.getDescription())
                            .build();
                    allergyRepo.save(allergy);
                });
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        if (dietaryRepo.count() == 0) {
            try {
                InputStream inputStream = getClass().getResourceAsStream("/dietaries.json");
                List<AllergyDietarySeed> dietarySeeds = objectMapper.readValue(inputStream, new TypeReference<>() {
                });
                dietarySeeds.forEach(dietarySeed -> {
                    Dietary dietary = Dietary.builder()
                            .name(dietarySeed.getName())
                            .description(dietarySeed.getDescription())
                            .build();
                    dietaryRepo.save(dietary);
                });
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        if (foodRepo.count() == 0) {
            try {
                // Đọc file foods.json
                InputStream inputStream = getClass().getResourceAsStream("/foods.json");
                List<FoodSeed> foodSeeds = objectMapper.readValue(inputStream, new TypeReference<>() {
                });

                // Duyệt qua danh sách và thêm vào database
                for (FoodSeed foodSeed : foodSeeds) {
                    Food food = Food.builder()
                            .name(foodSeed.getName())
                            .image(foodSeed.getImage())
                            .description(foodSeed.getDescription())
                            .meal(Meal.valueOf(foodSeed.getMeal()))
                            .cookingMethod(foodSeed.getCookingMethod())
                            .build();
                    foodRepo.save(food);

                    Nutrition nutrition = Nutrition.builder()
                            .food(food)
                            .calories(foodSeed.getNutrition().getCalories())
                            .protein(foodSeed.getNutrition().getProtein())
                            .fat(foodSeed.getNutrition().getFat())
                            .carb(foodSeed.getNutrition().getCarb())
                            .fiber(foodSeed.getNutrition().getFiber())
                            .sugar(foodSeed.getNutrition().getSugar())
                            .sodium(foodSeed.getNutrition().getSodium())
                            .cholesterol(foodSeed.getNutrition().getCholesterol())
                            .build();
                    nutritionRepo.save(nutrition);

                    foodSeed.getIngredients().forEach(ingredientSeed -> {
                        Ingredient ingredient = Ingredient.builder()
                                .food(food)
                                .ingredientName(ingredientSeed.getName())
                                .usage(ingredientSeed.getUsage())
                                .isCore(ingredientSeed.getIsCore())
                                .build();
                        ingredientRepo.save(ingredient);
                    });

                    foodSeed.getSteps().forEach(stepSeed -> {
                        Step step = Step.builder()
                                .food(food)
                                .stepOrder(stepSeed.getOrder())
                                .description(stepSeed.getDescription())
                                .image(stepSeed.getImage())
                                .build();
                        stepRepo.save(step);
                    });
                    foodSeed.getAllergies().forEach(allergyName -> {
                        FoodAllergy foodAllergy = FoodAllergy.builder()
                                .food(food)
                                .allergy(allergyRepo.findByName(allergyName))
                                .build();
                        foodAllergyRepo.save(foodAllergy);
                    });
                    foodSeed.getDietaries().forEach(dietaryName -> {
                        FoodDietary foodDietary = FoodDietary.builder()
                                .food(food)
                                .dietary(dietaryRepo.findByName(dietaryName))
                                .build();
                        foodDietaryRepo.save(foodDietary);
                    });
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            if (userRepo.count() == 0) {
                Users customer = Users.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Nguyen Yang")
                        .email("dstnmtxii@gmail.com")
                        .password(passwordEncoder.encode("Tnmt12122003mt:)"))
                        .role(Role.CUSTOMER)
                        .status(UserStatus.VERIFIED)
                        .build();
                userRepo.save(customer);
                UserAllergy userAllergy = UserAllergy.builder()
                        .user(userRepo.findByName("Nguyen Yang").get())
                        .allergy(allergyRepo.findByName("Tôm"))
                        .build();
                userAllergyRepo.save(userAllergy);
                UserDietary userDietary = UserDietary.builder()
                        .user(userRepo.findByName("Nguyen Yang").get())
                        .dietary(dietaryRepo.findByName("Giàu protein"))
                        .build();
                userDietaryRepo.save(userDietary);
                Users admin = Users.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Minh Thien")
                        .email("mt12122003@gmail.com")
                        .password(passwordEncoder.encode("Tnmt12122003mt:)"))
                        .role(Role.ADMIN)
                        .status(UserStatus.VERIFIED)
                        .build();
                userRepo.save(admin);
                Premium premium = Premium.builder()
                        .users(userRepo.findByEmail("mt12122003@gmail.com").get())
                        .start(LocalDateTime.now())
                        .end(LocalDateTime.now().plusYears(3))
                        .premiumStatus(PremiumStatus.ACTIVE)
                        .build();
                premiumRepo.save(premium);
            }
        }
    }
}
