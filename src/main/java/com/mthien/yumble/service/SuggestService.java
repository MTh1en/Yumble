package com.mthien.yumble.service;

import com.mthien.yumble.entity.Users;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyResponse;
import com.mthien.yumble.repository.*;
import com.mthien.yumble.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestService {
    private final FoodRepo foodRepo;
    private final FoodAllergyRepo foodAllergyRepo;
    private final FoodDietaryRepo foodDietaryRepo;
    private final UserRepo userRepo;
    private final UserAllergyService userAllergyService;
    private final UserDietaryService userDietaryService;
    private final AccountUtils accountUtils;

//    public FoodResponse suggestFoodByPersonalization() {
//        Users user = accountUtils.getMyInfo();
//        List<UserAllergyResponse> userAllergy = userAllergyService.viewAllergiesByUser(user.getId());
//        List<A>
//        userAllergy.stream().map()
//    }
}
