package com.mthien.yumble.payload.response.dietary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DietaryResponse {
    private String id;
    private String name;
    private String description;
}
