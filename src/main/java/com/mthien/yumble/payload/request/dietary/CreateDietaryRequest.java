package com.mthien.yumble.payload.request.dietary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDietaryRequest {
    private String name;
    private String description;
}
