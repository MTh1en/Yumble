package com.mthien.yumble.payload.request.premium;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePremiumRequest {
    private String userId;
    private Long days;
}
