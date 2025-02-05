package com.mthien.yumble.payload.response.payment;

import com.mthien.yumble.entity.Enum.PaymentStatus;
import com.mthien.yumble.payload.response.premium.PremiumResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String id;
    private PremiumResponse premium;
    private Long code;
    private Integer amount;
    private LocalDateTime time;
    private PaymentStatus status;
}
