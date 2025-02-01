package com.mthien.yumble.service;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGPTService {

    @Value("${openai.api.key}")
    protected String apiKey;
    @Value("${openai.api.url}")
    protected String apiUrl; // URL của ChatGPT API

    public String getChatGPTResponse(String question) {
        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "system", "content", "Bạn là một trợ lý chuyên về gợi ý món ăn. Nếu người dùng hỏi về món ăn, hãy trả lời tầm 5 tên món ăn bất ký liên quan đến chủ để đó viết liền 1 dòng. Nếu câu hỏi không liên quan, hãy trả lời 'Hãy liên hệ với bộ phận hỗ trợ để biết thêm thông tin.'"),
                        Map.of("role", "user", "content", question))
        );

        try {
            String response = webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            return jsonResponse
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();
        }
    }
}

