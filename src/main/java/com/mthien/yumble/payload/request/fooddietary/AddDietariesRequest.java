package com.mthien.yumble.payload.request.fooddietary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddDietariesRequest {
    private String dietaryId;
    private String priority;
}
