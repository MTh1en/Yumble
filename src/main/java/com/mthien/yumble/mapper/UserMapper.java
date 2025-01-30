package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Enum.Role;
import com.mthien.yumble.entity.Enum.UserStatus;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.payload.request.auth.RegisterRequest;
import com.mthien.yumble.payload.request.user.UpdateProfileRequest;
import com.mthien.yumble.payload.response.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(defaultRole())")
    @Mapping(target = "status", expression = "java(defaultStatus())")
    Users register(RegisterRequest registerRequest);

    void updateProfile(@MappingTarget Users users, UpdateProfileRequest request);

    UserResponse toUserResponse(Users users);

    @Mapping(target = "role", expression = "java(defaultRole())")
    @Mapping(target = "status", expression = "java(defaultStatus())")
    Users toUsers(Users users);

    List<UserResponse> toListUserResponse(List<Users> users);

    default Role defaultRole() {
        return Role.CUSTOMER;
    }

    default UserStatus defaultStatus() {
        return UserStatus.UNVERIFIED;
    }
}
