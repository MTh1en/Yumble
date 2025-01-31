package com.mthien.yumble.payload.response.user.allergy;

import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.payload.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAllergyDetailResponse {
    private UserResponse user;
    private AllergyResponse allergy;
    private String severity;
}
