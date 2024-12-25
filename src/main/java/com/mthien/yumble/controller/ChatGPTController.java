package com.mthien.yumble.controller;

import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.service.ChatGPTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping("/chatgpt")
    public ResponseEntity<ApiResponse<String>> askQuestion(@RequestBody String question) {
        var data = chatGPTService.getChatGPTResponse(question);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("ChatGPT trả lời câu hỏi của bạn")
                .data(data)
                .build());
    }

}
