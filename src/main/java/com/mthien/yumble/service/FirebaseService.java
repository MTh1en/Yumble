package com.mthien.yumble.service;

import com.google.cloud.storage.*;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseService {
    @Value("${firebase.bucket-name}")
    protected String firebaseBucketName;

    private final Storage storage;

    public FirebaseService(Storage storage) {
        this.storage = storage;
    }

    public String generateUniqueFileName(String fileName) {
        String uniqueId = UUID.randomUUID().toString();
        return String.format("%s - %s", uniqueId, fileName);
    }

    public String uploadFile(String fileName, MultipartFile file) throws IOException {
        Bucket bucket = storage.get(firebaseBucketName);
        String uniqueFileName = generateUniqueFileName(fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), uniqueFileName)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getBytes());
        return String.format("%s", uniqueFileName);
    }

    public String getImageUrl(String baseImageUrl) throws IOException {
        URL signedUrl = storage.signUrl(
                BlobInfo.newBuilder(BlobId.of(firebaseBucketName, baseImageUrl)).build(),
                1, TimeUnit.DAYS,
                Storage.SignUrlOption.withV4Signature());
        return signedUrl.toString();
    }


    public void deleteFile(String fileName) {
        Bucket bucket = storage.get(firebaseBucketName);
        Blob blob = bucket.get(fileName);
        if (blob != null) {
            blob.delete();
        } else {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }
    }
}
