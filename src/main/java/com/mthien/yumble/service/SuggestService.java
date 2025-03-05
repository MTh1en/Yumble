package com.mthien.yumble.service;

import com.mthien.yumble.entity.*;
import com.mthien.yumble.mapper.FoodMapper;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.*;
import com.mthien.yumble.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<FoodResponse> suggestFoodByPersonalization(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Users user = accountUtils.getMyInfo();
        List<String> userAllergies = userAllergyRepo.findByUser(user).stream()
                .map(UserAllergy::getAllergy)
                .map(Allergy::getId)
                .toList();
        List<String> userDietaries = userDietaryRepo.findByUser(user).stream()
                .map(UserDietary::getDietary)
                .map(Dietary::getId)
                .toList();
        Page<Food> recommendedFoods;
        if (userAllergies.isEmpty() && userDietaries.isEmpty()) {
            recommendedFoods = foodRepo.findAll(pageable);
        } else {
            recommendedFoods = foodRepo.findRecommendedFoods(userAllergies.isEmpty() ? null : userAllergies,
                    userDietaries.isEmpty() ? null : userDietaries, pageable);
        }
        return recommendedFoods.stream().map(food -> {
            String image = Optional.ofNullable(food.getImage())
                    .filter(imageUrl -> imageUrl.contains("food"))
                    .map(firebaseService::getImageUrl)
                    .orElse(food.getImage());
            return foodMapper.toFoodResponse(food, image);
        }).collect(Collectors.toList());
    }
}
