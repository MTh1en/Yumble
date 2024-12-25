package com.mthien.yumble.config;

import com.mthien.yumble.entity.Enum.Role;
import com.mthien.yumble.entity.Enum.UserStatus;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public DataLoader(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) {
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
    }
}
