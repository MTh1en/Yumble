package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.UserAllergy;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.payload.request.user.dietary.AddUserAllergyRequest;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyDetailResponse;
import com.mthien.yumble.payload.response.user.allergy.UserAllergyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAllergyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "allergy", expression = "java(allergy)")
    UserAllergy addUserAllergy(Users user, Allergy allergy, AddUserAllergyRequest request);

    UserAllergyDetailResponse toUserAllergyDetailResponse(UserAllergy userAllergy);

    UserAllergyResponse toUserAllergyResponse(UserAllergy userAllergy);
}
