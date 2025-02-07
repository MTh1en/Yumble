package com.mthien.yumble.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mthien.yumble.mapper.FoodMapper;
import com.mthien.yumble.payload.response.CustomOpenAIResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.repository.FoodRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGPTService {

    private final ChatModel chatModel;
    private final FoodRepo foodRepo;
    private final FoodMapper foodMapper;

    public CustomOpenAIResponse chat(String question) {
        String message = generateAIResponse(question);
        List<FoodResponse> foods = getFoodName(message).stream().map(foodRepo::findByNameIgnoreCase).map(foodMapper::toFoodResponse).toList();
        return CustomOpenAIResponse.builder()
                .message(message)
                .foods(foods)
                .build();
    }

    public String generateAIResponse(String message) {
        String systemMessage = "Bún chả, Gỏi cuốn, Bánh xèo, Bánh mì, Cơm tấm, Bánh cuốn, Chả giò, Cá kho tộ, Bánh khọt, Cao lầu, Bún bò Huế, Mì Quảng, Lẩu mắm, Bánh đa cua, " +
                "Cháo lươn, Nem chua, Bún thang, Bánh gai, Xôi gấc, Ốc luộc, Phở bò, Phở gà, Hủ tiếu Nam Vang, " +
                "Bánh bột lọc, Bún riêu cua, Nem lụi, Chả cá Lã Vọng, Gỏi cuốn tôm thịt, Lẩu cá kèo, Cơm cháy chà bông, " +
                "Bún mắm, Chè ba màu, Ốc xào me, Bánh bèo, Bò kho, Cháo lòng, Bún đậu mắm tôm, Bánh căn, Bánh hỏi thịt nướng, " +
                "Canh chua cá lóc, Gỏi gà xé phay, Bánh đập, Bánh canh cua, Bánh tét, Bánh mì thịt nướng, Lẩu dê, " +
                "Bánh tráng trộn, Bún chả cá, Bánh cuốn Thanh Trì, Chè trôi nước, Bánh đa kê, Bánh khoái, Bún mọc, " +
                "Chả mực Hạ Long, Bánh nậm, Cơm hến, Bánh đúc, Nem nướng Nha Trang, Bánh mì thập cẩm, Bún thịt nướng, " +
                "Cháo ấu tẩu, Bún suông, Bánh chuối hấp, Cơm gà Hội An, Bún quậy Phú Quốc, Bún cá, Bánh gối, " +
                "Canh khổ qua nhồi thịt, Chả đùm, Gà nướng đất sét, Xôi khúc, Nem cua bể, Gỏi cá mai, Mắm tép chưng thịt, " +
                "Chạo tôm, Chè kho, Gỏi ngó sen, Nem rán, Bún ốc, Xôi xéo, Chè khúc bạch, Bánh ít lá gai, Lẩu bắp hoa, " +
                "Canh bầu nấu tôm, Bánh ít trần, Cá kho làng Vũ Đại, Gà kho gừng, Lẩu bò, Gà nướng lá chanh, Chả cá Thăng Long, " +
                "Bún cá rô đồng, Cơm lam, Chả lụa, Thịt kho tàu, Cá chép om dưa, Bò nhúng dấm, Canh bún, Gỏi đu đủ, " +
                "Bánh tằm bì, Bún tôm Hải Phòng, Gà rút xương chiên giòn, Mì vịt tiềm, Bò bít tết, Bánh chưng, " +
                "Gỏi cá trích, Lẩu riêu cua, Ốc xào chuối đậu, Bò lá lốt, Cá lóc nướng trui, Gà hấp muối, Lẩu gà lá é, Chè Thái, " +
                "Canh bí đỏ tôm khô, Gỏi tôm thịt, Canh cà chua trứng, Bún thịt xào, Cá hấp xì dầu, Bún ngan, Bánh cống, " +
                "Bánh tôm Hồ Tây, Gỏi mít non, Lẩu lòng bò, Bún riêu, Bánh ướt, Mì tôm trứng, Bánh bao, Dim sum, Gỏi cuốn chay, " +
                "Bún chả giò, Cơm sườn, Canh ngao chua, Thịt ba chỉ rang cháy cạnh, Đậu phụ sốt cà chua, Rau muống xào tỏi, " +
                "Canh rau ngót nấu thịt bằm, Cá diêu hồng chiên xù, Tôm rim thịt, Sườn nướng, Lẩu thái, Bánh tráng nướng, " +
                "Chân gà nướng, Bánh khoai mì nướng, Kem, Chè, Sinh tố, Bánh xèo miền Trung, Bún bò Huế chay, Mì Quảng chay, " +
                "Gỏi cá trích Phú Quốc, Bánh tráng cuốn thịt heo Đà Nẵng, Chả cá Nha Trang, Nem chua rán, Phở cuốn, Nộm gà, " +
                "Nộm đu đủ, Gỏi xoài, Bún riêu cua đồng, Bún cá, Canh chua cá lóc, Gà hấp muối, Chè Thái, Canh bí đỏ tôm khô, " +
                "Gỏi tôm thịt, Canh cà chua trứng, Bún thịt xào, Cá hấp xì dầu, Bún ngan, Bánh cống, Bánh tôm Hồ Tây, " +
                "Gỏi mít non, Lẩu lòng bò, Xôi gà, Bún đậu phụ mắm tôm";
        Prompt prompt = new Prompt(
                List.of(
                        new SystemMessage("Bạn là một trợ lý chuyên gia về ẩm thực việt nam. dựa vào tên món ăn tôi cung cấp:" + systemMessage +
                                "Hãy trả đề xuất món ăn dựa trên những cái tên trên" +
                                "Nếu câu hỏi không liên quan, hãy trả lời 'Hãy liên hệ với bộ phận hỗ trợ để biết thêm thông tin."),
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

