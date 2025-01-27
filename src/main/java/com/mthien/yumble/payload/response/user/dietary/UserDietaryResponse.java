package com.mthien.yumble.payload.response.user.dietary;

import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import com.mthien.yumble.payload.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDietaryResponse {
    private UserResponse user;
    private DietaryResponse dietary;
    private String priority;
}
