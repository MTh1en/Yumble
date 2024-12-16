package com.mthien.yumble.service;

import com.google.cloud.storage.*;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FirebaseService {
    @Value("${firebase.image-url}")
    protected String firebaseImageUrl;
    @Value("${firebase.bucket-name}")
    protected String firebaseBucketName;

    private final Storage storage;

    public FirebaseService(Storage storage) {
        this.storage = storage;
    }

    public String uploadFile(String fileName, MultipartFile file) throws IOException {
        Bucket bucket = storage.get(firebaseBucketName);
        String downloadToken = UUID.randomUUID().toString();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), fileName)
                .setContentType(file.getContentType())
                .setMetadata(Map.of("firebaseStorageDownloadTokens", downloadToken))
                .build();
        storage.create(blobInfo, file.getBytes());
        return String.format(firebaseImageUrl, fileName, downloadToken);
    }

    public String getImageUrl(String fileName) {
        Bucket bucket = storage.get(firebaseBucketName);
        Blob blob = bucket.get(fileName);
        if (blob == null) {
            throw new RuntimeException("Image not found.");
        }
        String token = blob.getMetadata().get("firebaseStorageDownloadTokens");
        return String.format(firebaseImageUrl, fileName, token);
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
