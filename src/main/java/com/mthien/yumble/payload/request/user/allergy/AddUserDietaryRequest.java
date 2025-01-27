package com.mthien.yumble.payload.request.user.allergy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserDietaryRequest {
    private String dietaryId;
    private String priority;
}
