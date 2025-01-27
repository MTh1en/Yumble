package com.mthien.yumble.payload.request.user.dietary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserAllergyRequest {
    private String allergyId;
    private String severity;
}
