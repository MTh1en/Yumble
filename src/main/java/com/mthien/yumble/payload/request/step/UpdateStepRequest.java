package com.mthien.yumble.payload.request.step;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStepRequest {
    private Integer stepOrder;
    private String description;
}
