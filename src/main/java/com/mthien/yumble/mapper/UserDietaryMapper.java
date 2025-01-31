package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.UserDietary;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.payload.request.user.allergy.AddUserDietaryRequest;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryDetailResponse;
import com.mthien.yumble.payload.response.user.dietary.UserDietaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDietaryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "dietary", expression = "java(dietary)")
    UserDietary addUserDietary(Users user, Dietary dietary, AddUserDietaryRequest request);

    UserDietaryDetailResponse toUserDietaryDetailResponse(UserDietary userDietary);

    UserDietaryResponse toUserDietaryResponse(UserDietary userDietary);
}
