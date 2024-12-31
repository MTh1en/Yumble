package com.mthien.yumble.payload.request.dietary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDietaryRequest {
    private String name;
    private String description;
}
