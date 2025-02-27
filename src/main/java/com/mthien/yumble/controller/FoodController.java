package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.food.CreateFoodRequest;
import com.mthien.yumble.payload.request.food.UpdateFoodRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.food.FoodResponse;
import com.mthien.yumble.service.FoodService;
import com.mthien.yumble.service.SuggestService;
import com.mthien.yumble.utils.AccountUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final SuggestService suggestService;
    public final AccountUtils accountUtils;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FoodResponse> create(@ModelAttribute CreateFoodRequest request) {
        return ApiResponse.<FoodResponse>builder()
                .message("Tạo thông tin món ăn thành công")
                .data(foodService.create(request))
                .build();
    }

    @CacheEvict(value = "foods", allEntries = true)
    @PatchMapping("{foodId}")
    public ApiResponse<FoodResponse> update(@PathVariable("foodId") String foodId,
                                            @RequestBody UpdateFoodRequest request) {

        return ApiResponse.<FoodResponse>builder()
                .message("Cập nhật thông tin món ăn thành công")
                .data(foodService.update(foodId, request))
                .build();
    }

    @CacheEvict(value = "foods", allEntries = true)
    @PatchMapping(value = "/{foodId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FoodResponse> uploadImage(@PathVariable("foodId") String foodId,
                                                 @RequestParam("image") MultipartFile image) {
        return ApiResponse.<FoodResponse>builder()
                .message("Tải hình ảnh món ăn thành công")
                .data(foodService.uploadFoodImage(foodId, image))
                .build();
    }

    @GetMapping("/{foodId}")
    public ApiResponse<FoodResponse> viewOne(@PathVariable("foodId") String foodId) {
        return ApiResponse.<FoodResponse>builder()
                .message("thông tin món ăn")
                .data(foodService.viewOne(foodId))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<FoodResponse>> viewAll( @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<List<FoodResponse>>builder()
                .message("thông tin cơ bản món ăn")
                .data(foodService.viewAll(page, size))
                .build();
    }

    @CacheEvict(value = "foods", allEntries = true)
    @DeleteMapping("/{foodId}")
    public ApiResponse<Void> delete(@PathVariable("foodId") String foodId) {
        foodService.delete(foodId);
        return ApiResponse.<Void>builder()
                .message("Xóa món ăn thành công")
                .build();
    }

    @Cacheable(value = "foods", key = "root.target.accountUtils.getMyInfo().getId()")
    @GetMapping("/suggestion")
    public ApiResponse<List<FoodResponse>> suggest() {
        return ApiResponse.<List<FoodResponse>>builder()
                .message("Dề xuất theo cá nhân")
                .data(suggestService.suggestFoodByPersonalization())
                .build();
    }
}
