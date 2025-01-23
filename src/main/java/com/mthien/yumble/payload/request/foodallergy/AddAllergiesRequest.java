package com.mthien.yumble.payload.request.foodallergy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddAllergiesRequest {
    private String allergyId;
    private String severity;
}
