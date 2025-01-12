package com.mthien.yumble.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddAllergiesAndDietariesRequest {
    private Set<String> allergies;
    private Set<String> dietaries;
}
