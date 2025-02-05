package com.mthien.yumble.payload.request.premium;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivePremiumRequest {
    private String userId;

    private Long days;
}
