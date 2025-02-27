package com.mthien.yumble.payload.response.user.dietary;

import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDietaryResponse {
    private DietaryResponse dietary;
    private String priority;
}
