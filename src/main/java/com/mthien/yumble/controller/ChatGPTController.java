package com.mthien.yumble.controller;

import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/chatgpt")
    public ApiResponse<String> askQuestion(@RequestBody String question) {
        return ApiResponse.<String>builder()
                .message("ChatGPT trả lời câu hỏi của bạn")
                .data(chatGPTService.getChatGPTResponse(question))
                .build();
    }

}
