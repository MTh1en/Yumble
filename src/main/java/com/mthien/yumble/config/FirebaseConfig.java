package com.mthien.yumble.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Bean
    public Storage firebaseStorage() throws IOException {
        ClassPathResource serviceAccount = new ClassPathResource("yumble-firebase-key.json");
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .build()
                .getService();
    }
}
