package com.mthien.yumble.payload.response.methodcooking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MethodCookingResponse {
    private String id;
    private String name;
    private String description;
}
