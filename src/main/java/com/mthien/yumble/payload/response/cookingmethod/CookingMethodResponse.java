package com.mthien.yumble.payload.response.cookingmethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookingMethodResponse {
    private String id;
    private String name;
    private String description;
}
