package com.mthien.yumble.payload.response.user;

import com.mthien.yumble.entity.Enum.Role;
import com.mthien.yumble.entity.Enum.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String dietary;
    private String allergy;
    private UserStatus status;
}
