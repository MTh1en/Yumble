package com.mthien.yumble.payload.request.food;

import com.mthien.yumble.entity.Enum.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFoodRequest {
    private String name;
    private String description;
    private MultipartFile image;
    private Meal meal;
    private String cookingMethod;
}
