package com.mthien.yumble.service;

import com.google.cloud.storage.*;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseService {
    @Value("${firebase.bucket.name}")
    protected String firebaseBucketName;

    private final Storage storage;

    public String uploadFile(String fileName, MultipartFile file) {
        try {
            Bucket bucket = storage.get(firebaseBucketName);
            BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), fileName)
                    .setContentType(file.getContentType())
                    .build();
            storage.create(blobInfo, file.getBytes());
            return String.format("%s", fileName);
        } catch (IOException e) {
            log.error("Error while uploading file: ", e);
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    public String getImageUrl(String baseImageUrl) {
        URL signedUrl = storage.signUrl(
                BlobInfo.newBuilder(BlobId.of(firebaseBucketName, baseImageUrl))
                        .setContentType("model/gltf-binary").build(),
                1, TimeUnit.HOURS,
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
