package com.mthien.yumble.config;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.entity.Enum.Role;
import com.mthien.yumble.entity.Enum.UserStatus;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    private final DietaryRepo dietaryRepo;
    private final AllergyRepo allergyRepo;

    @Override
    public void run(ApplicationArguments args) {
        //USER
        if (userRepo.count() == 0) {
            Users user1 = Users.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Nguyen Yang")
                    .email("dstnmtxii@gmail.com")
                    .password(passwordEncoder.encode("tnmt12122003mt"))
                    .role(Role.CUSTOMER)
                    .status(UserStatus.VERIFIED)
                    .build();
            Users user2 = Users.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Minh Thien")
                    .email("mt121222003@gmail.com")
                    .password(passwordEncoder.encode("tnmt12122003mt"))
                    .role(Role.ADMIN)
                    .status(UserStatus.VERIFIED)
                    .build();
            userRepo.save(user1);
            userRepo.save(user2);
        }

        if (dietaryRepo.count() == 0) {
            String[] dietaries = {
                    "Truyền thống Việt-Sử dụng gạo, rau xanh, thực phẩm tươi, ít chất béo; cân bằng dinh dưỡng giữa protein động vật và thực vật.",
                    "Thực vật-Tập trung vào thực phẩm từ thực vật như rau, củ, quả, hạt, và đậu; không sử dụng hoặc hạn chế thực phẩm động vật.",
                    "Chay-Không sử dụng thịt, cá, hải sản; sử dụng thực phẩm từ trứng, sữa và các sản phẩm thực vật.",
                    "Thuần chay-Không sử dụng bất kỳ sản phẩm nào từ động vật, bao gồm thịt, trứng, sữa, mật ong.",
                    "Ít tinh bột-Hạn chế tinh bột từ gạo, bánh mì, mì, thay thế bằng thực phẩm giàu protein và chất béo tốt.",
                    "Giàu protein-Tăng cường thực phẩm giàu protein từ thịt, cá, trứng, đậu; giảm tinh bột và chất béo không lành mạnh.",
                    "Ít chất béo-Giảm chất béo từ dầu mỡ, bơ; tăng cường thực phẩm tươi như rau xanh, trái cây, và thịt nạc.",
                    "Kiểu Địa Trung Hải-Sử dụng dầu ô liu, cá, rau củ, ngũ cốc nguyên cám, hạt và trái cây; giảm thịt đỏ và thực phẩm chế biến.",
                    "Keto-Hạn chế gần như hoàn toàn tinh bột, tăng cường chất béo tốt và protein; thúc đẩy trạng thái ketosis trong cơ thể.",
                    "Cho người tiểu đường-Kiểm soát lượng đường và carbohydrate, sử dụng thực phẩm có chỉ số GI thấp, tăng cường rau xanh và protein nạc.",
                    "Không gây dị ứng-Loại bỏ các thực phẩm gây dị ứng như gluten, sữa, đậu phộng, hải sản, hoặc trứng tùy thuộc vào nhu cầu cá nhân.",
                    "Bán chay-Chủ yếu sử dụng thực phẩm thực vật nhưng thỉnh thoảng có thêm thịt, cá hoặc hải sản.",
                    "Nhịn ăn gián đoạn-Không tập trung vào loại thực phẩm mà quản lý thời gian ăn (ví dụ: nhịn ăn 16 giờ và ăn trong 8 giờ).",
                    "Không gluten-Loại bỏ thực phẩm chứa gluten từ lúa mì, lúa mạch và ngũ cốc; sử dụng thực phẩm từ gạo, ngô, khoai, và sắn.",
            };
            List<Dietary> dietaryList = Arrays.stream(dietaries).map(entry -> {
                String[] parts = entry.split("-", 2);
                return Dietary.builder()
                        .name(parts[0])
                        .description(parts[1])
                        .build();
            }).toList();
            dietaryRepo.saveAll(dietaryList);
        }

        if (allergyRepo.count() == 0) {
            String[] allergies = {
                    "Đậu phộng-Phản ứng miễn dịch với protein trong đậu phộng, thường nghiêm trọng, có thể gây sốc phản vệ.",
                    "Hạt cây-Phản ứng với hạt từ cây, khác với đậu phộng, có thể nghiêm trọng như đậu phộng.",
                    "Sữa-Phản ứng với protein trong sữa bò (casein hoặc whey), không phải không dung nạp lactose.",
                    "Trứng-Phản ứng với protein trong lòng trắng hoặc lòng đỏ trứng, thường gặp ở trẻ nhỏ.",
                    "Gluten-Phản ứng với gluten, một loại protein trong lúa mì, lúa mạch và lúa mạch đen.",
                    "Hải sản-Phản ứng với protein trong hải sản, có thể gây sốc phản vệ nghiêm trọng.",
                    "Đậu nành-Phản ứng với protein trong đậu nành, phổ biến ở trẻ em và có thể giảm khi trưởng thành.",
                    "Cá-Phản ứng với protein trong cá, khác với dị ứng hải sản.",
                    "Trái cây-Phản ứng với các protein trong trái cây, thường liên quan đến hội chứng dị ứng phấn hoa.",
                    "Hạt mè-Phản ứng với protein trong hạt mè, đang ngày càng phổ biến.",
                    "Bắp-Phản ứng với protein trong ngô, ít phổ biến hơn nhưng vẫn gặp ở một số người.",
                    "Phẩm màu thực phẩm-Phản ứng với các chất phụ gia hoặc phẩm màu nhân tạo trong thực phẩm.",
                    "Chất bảo quản-Phản ứng với các chất bảo quản thực phẩm.",
                    "Thịt động vật-Phản ứng với protein trong thịt đỏ hoặc thịt gia cầm, liên quan đến dị ứng alpha-gal.",
                    "Sữa ong chúa-Phản ứng với mật ong hoặc sữa ong chúa, thường gây ngứa, sưng môi, và phát ban.",
            };
            List<Allergy> allergyList = Arrays.stream(allergies).map(entry -> {
                String[] parts = entry.split("-", 2);
                return Allergy.builder()
                        .name(parts[0])
                        .description(parts[1])
                        .build();
            }).toList();
            allergyRepo.saveAll(allergyList);
        }
    }
}
