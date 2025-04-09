package com.mthien.yumble.controller;

import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.CustomOpenAIResponse;
import com.mthien.yumble.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/chatgpt")
    public ApiResponse<CustomOpenAIResponse> askQuestion(@RequestBody String question) {
        return ApiResponse.<CustomOpenAIResponse>builder()
                .message("ChatGPT trả lời câu hỏi của bạn")
                .data(chatGPTService.chat(question))
                .build();
    }

    @PostMapping("/generate-image")
    public ApiResponse<ImageResponse> generatedImage(@RequestBody String question) {
        return ApiResponse.<ImageResponse>builder()
                .message("ChatGPT trả lời câu hỏi của bạn")
                .data(chatGPTService.generatedImage(question))
                .build();
    }
}
