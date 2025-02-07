package com.mthien.yumble.payload.response;

import com.mthien.yumble.payload.response.food.FoodResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomOpenAIResponse {
    private String message;
    private List<FoodResponse> foods;
}
