package com.mthien.yumble.payload.request.payos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentLink {
    private String userId;
    private Boolean isMonthPremium;
}
