package com.mthien.yumble.payload.request.payment;

import com.mthien.yumble.entity.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentRequest {
    private Long code;
    private Integer amount;
    private LocalDateTime time;
    private PaymentStatus status;
}
