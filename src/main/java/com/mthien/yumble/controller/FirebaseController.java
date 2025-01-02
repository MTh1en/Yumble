package com.mthien.yumble.controller;

import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.service.FirebaseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("firebase")
public class FirebaseController {
    private final FirebaseService firebaseService;

    public FirebaseController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> updateProfile(@RequestParam("name") String name,
                                                             @RequestParam("file") MultipartFile file) throws IOException {
        var data = firebaseService.uploadFile(name, file);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Upload ảnh thành công")
                .data(data)
                .build());
    }

    @GetMapping("image-url")
    public ResponseEntity<ApiResponse<String>> getImageUrl(@RequestParam String imageName) {
        var data = firebaseService.getImageUrl(imageName);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("đường dẫn hình ảnh: " + imageName)
                .data(data)
                .build());
    }

    @PostMapping("delete")
    public ResponseEntity<ApiResponse<String>> deleteFile(@RequestParam("name") String name) {
        firebaseService.deleteFile(name);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Xóa file với tên " + name + " thành công")
                .build());
    }
}
