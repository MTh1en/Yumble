package com.mthien.yumble.controller;

import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatGPTController {

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping("/ask")
    public ResponseEntity<ApiResponse<String>> askQuestion(@RequestBody String question) {
        var data = chatGPTService.getChatGPTResponse(question);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("ChatGPT trả lời câu hỏi của bạn")
                .data(data)
                .build());
    }

}
