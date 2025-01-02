package com.mthien.yumble.payload.response.step;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StepResponse {
    private String id;
    private String foodId;
    private String image;
    private Integer stepOrder;
    private String description;
}
