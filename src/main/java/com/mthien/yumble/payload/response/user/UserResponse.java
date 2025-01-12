package com.mthien.yumble.payload.response.user;

import com.mthien.yumble.entity.Enum.Role;
import com.mthien.yumble.entity.Enum.UserStatus;
import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String avatar;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private UserStatus status;
    private Set<AllergyResponse> allergies;
    private Set<DietaryResponse> dietaries;
}
