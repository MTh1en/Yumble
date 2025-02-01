package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.step.CreateStepRequest;
import com.mthien.yumble.payload.request.step.UpdateStepRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.step.StepDetailResponse;
import com.mthien.yumble.payload.response.step.StepResponse;
import com.mthien.yumble.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("foods")
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;

    @PostMapping(value = "{foodId}/steps", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<StepDetailResponse> addStepToFood(@PathVariable("foodId") String id,
                                                         @ModelAttribute CreateStepRequest request) {
        return ApiResponse.<StepDetailResponse>builder()
                .message("Đã thêm bước vào món ăn thành công")
                .data(stepService.addStepToFood(id, request))
                .build();
    }

    @PatchMapping("/steps/{stepId}")
    public ApiResponse<StepDetailResponse> updateStepInformation(@PathVariable("stepId") String id,
                                                                 @RequestBody UpdateStepRequest request) {
        return ApiResponse.<StepDetailResponse>builder()
                .message("Cập nhật thông tin các bước thành công")
                .data(stepService.updateStepInformation(id, request))
                .build();
    }

    @PatchMapping(value = "/steps/{stepId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<StepDetailResponse> updateStepImage(@PathVariable("stepId") String stepId,
                                                           @RequestParam("file") MultipartFile file) {
        return ApiResponse.<StepDetailResponse>builder()
                .message("Cập nhật hình ảnh các bước thành công")
                .data(stepService.updateStepImage(stepId, file))
                .build();
    }

    @GetMapping("/{foodId}/steps")
    public ApiResponse<List<StepResponse>> viewStepByFood(@PathVariable("foodId") String id) {
        return ApiResponse.<List<StepResponse>>builder()
                .message("Thông tin các bước nấu ăn của món")
                .data(stepService.viewStepsByFood(id))
                .build();
    }

    @DeleteMapping("/steps/{stepId}")
    public ApiResponse<Void> deleteStep(@PathVariable("stepId") String id) {
        stepService.deleteStep(id);
        return ApiResponse.<Void>builder()
                .message("Đã xóa bước nấu món ăn thành công")
                .build();
    }
}
