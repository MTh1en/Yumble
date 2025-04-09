package com.mthien.yumble.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mthien.yumble.entity.Enum.PremiumStatus;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.FoodMapper;
import com.mthien.yumble.payload.response.CustomOpenAIResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.repository.PremiumRepo;
import com.mthien.yumble.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGPTService {

    private final ChatModel chatModel;
    private final FoodRepo foodRepo;
    private final FoodMapper foodMapper;
    private final AccountUtils accountUtils;
    private final PremiumRepo premiumRepo;
    private final ImageModel imageModel;

    public ImageResponse generatedImage(String question) {
        return imageModel.call(
                new ImagePrompt(question,
                        OpenAiImageOptions.builder()
                                .model("dall-e-2")
                                .quality("hd")
                                .N(1)
                                .height(512)
                                .width(512).build())
        );
    }

    public CustomOpenAIResponse chat(String question) {
        String message = generateAIResponse(question);
        List<FoodResponse> foods = getFoodName(message).stream()
                .map(foodRepo::findByNameIgnoreCase)
                .filter(Objects::nonNull)
                .map(foodMapper::toFoodResponse).toList();
        return CustomOpenAIResponse.builder()
                .message(message)
                .foods(foods)
                .build();
    }

    public String generateAIResponse(String message) {
        Users users = accountUtils.getMyInfo();
        if (users == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (!premiumRepo.existsByUsersAndPremiumStatus(users, PremiumStatus.ACTIVE)) {
            throw new AppException(ErrorCode.PREMIUM_NOT_REGISTERED);
        }
        String foodList = String.join(", ", foodRepo.findFoodNameForAI());
        String systemMessage = "Bạn là một trợ lý chuyên gia về ẩm thực Việt Nam. Dựa vào danh sách món ăn tôi cung cấp: " + foodList + ". " +
                "Nếu người dùng yêu cầu đề xuất món ăn, hãy đề xuất từ danh sách này" +
                "Nếu người dùng hỏi công thức, cách làm, hoặc thông tin chi tiết về một món ăn, hãy cung cấp câu trả lời ngăn gọn xúc tích dựa trên kiến thức của bạn" +
                "Lưu ý: món ăn đó nằm trong danh sách, chỉ in đậm tên món ăn ở lần đề cập đầu tiên trong câu trả lời, các lần sau thì không in đậm." +
                "Nếu câu hỏi không liên quan đến ẩm thực, thì cứ trả lời theo dữ liệu của bạn";
        Prompt prompt = new Prompt(
                List.of(
                        new SystemMessage(systemMessage),
                        new UserMessage(message)
                )
        );
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getContent();
    }


    public List<String> getFoodName(String input) {
        List<String> foodNameList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\*\\*(.+?)\\*\\*");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            foodNameList.add(matcher.group(1));
        }
        return foodNameList;
    }

}

