package com.mthien.yumble.payload.request.foodcookingmethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCookingMethodRequest {
    private String cookingMethodId;
    private Float timeRequired;
    private Boolean isCore;
}
