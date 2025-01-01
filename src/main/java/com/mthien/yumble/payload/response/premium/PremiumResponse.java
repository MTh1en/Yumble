package com.mthien.yumble.payload.response.premium;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.payload.response.user.UserResponse;
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
    private UserResponse users;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long remaining;
}
