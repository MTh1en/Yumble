package com.mthien.yumble.payload.request.cookingmethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCookingMethodRequest {
    private String name;
    private String description;
}
