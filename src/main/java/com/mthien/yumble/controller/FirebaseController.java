package com.mthien.yumble.controller;

import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("firebase")
@RequiredArgsConstructor
public class FirebaseController {
    private final FirebaseService firebaseService;

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> updateProfile(@RequestParam("name") String name,
                                             @RequestParam("file") MultipartFile file) {
        return ApiResponse.<String>builder()
                .message("Upload ảnh thành công")
                .data(firebaseService.uploadFile(name, file))
                .build();
    }

    @GetMapping("image-url")
    public ApiResponse<String> getImageUrl(@RequestParam String fileName) {
        return ApiResponse.<String>builder()
                .message("Lấy đường dẫn của hình ảnh thành công")
                .data(firebaseService.getImageUrl(fileName))
                .build();
    }

    @PostMapping("delete")
    public ApiResponse<String> deleteFile(@RequestParam("name") String name) {
        firebaseService.deleteFile(name);
        return ApiResponse.<String>builder()
                .message("Xóa file thành công")
                .build();
    }
}
