package com.mthien.yumble.service;

import com.mthien.yumble.entity.*;
import com.mthien.yumble.mapper.FoodMapper;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.*;
import com.mthien.yumble.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SuggestService {
    private final FoodRepo foodRepo;
    private final FoodMapper foodMapper;
    private final UserAllergyRepo userAllergyRepo;
    private final UserDietaryRepo userDietaryRepo;
    private final AccountUtils accountUtils;
    private final FirebaseService firebaseService;

    public List<FoodResponse> suggestFoodByPersonalization() {
        Users user = accountUtils.getMyInfo();
        List<Allergy> userAllergies = userAllergyRepo.findByUser(user).stream()
                .map(UserAllergy::getAllergy)
                .toList();
        List<Dietary> userDietaries = userDietaryRepo.findByUser(user).stream()
                .map(UserDietary::getDietary)
                .toList();
        List<Food> recommendedFoods = foodRepo.findAll().stream()
                .filter(food -> userAllergies.isEmpty() || food.getFoodAllergies().stream()
                        .noneMatch(foodAllergy -> userAllergies.contains(foodAllergy.getAllergy()))
                )
                .filter(food -> userDietaries.isEmpty() || food.getFoodDietaries().stream()
                        .anyMatch(foodDietary -> userDietaries.contains(foodDietary.getDietary()))
                )
                .toList();

        return recommendedFoods.stream().map(food -> {
            String image = Optional.ofNullable(food.getImage())
                    .filter(imageUrl -> imageUrl.contains("food"))
                    .map(firebaseService::getImageUrl)
                    .orElse(food.getImage());
            return foodMapper.toFoodResponse(food, image);
        }).collect(Collectors.toList());
    }
}
