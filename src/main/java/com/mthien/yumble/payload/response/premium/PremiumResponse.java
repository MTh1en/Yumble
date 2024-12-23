package com.mthien.yumble.payload.response.premium;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PremiumResponse {
    private String id;
    private String userId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long remaining;
}