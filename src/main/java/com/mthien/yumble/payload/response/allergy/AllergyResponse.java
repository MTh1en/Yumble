package com.mthien.yumble.payload.response.allergy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllergyResponse {
    private String id;
    private String name;
    private String description;
}
