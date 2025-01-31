package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.step.CreateStepRequest;
import com.mthien.yumble.payload.request.step.UpdateStepRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.step.FoodStepResponse;
import com.mthien.yumble.payload.response.step.StepResponse;
import com.mthien.yumble.service.StepService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("foods")
public class StepController {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping(value = "{foodId}/steps", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FoodStepResponse>> addStepToFood(@PathVariable("foodId") String id,
                                                                       @ModelAttribute CreateStepRequest request) {
        var data = stepService.addStepToFood(id, request);
        return ResponseEntity.ok(ApiResponse.<FoodStepResponse>builder()
                .code(200)
                .message("Đã thêm bước vào món ăn thành công")
                .data(data)
                .build());
    }

    @PatchMapping("/steps/{stepId}")
    public ResponseEntity<ApiResponse<FoodStepResponse>> updateStepInformation(@PathVariable("stepId") String id,
                                                                           @RequestBody UpdateStepRequest request) {
        var data = stepService.updateStepInformation(id, request);
        return ResponseEntity.ok(ApiResponse.<FoodStepResponse>builder()
                .code(200)
                .message("Cập nhật thông tin các bước thành công")
                .data(data)
                .build());
    }

    @PatchMapping(value = "/steps/{stepId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FoodStepResponse>> updateStepImage(@PathVariable("stepId") String stepId,
                                                                     @RequestParam("file") MultipartFile file) {
        var data = stepService.updateStepImage(stepId, file);
        return ResponseEntity.ok(ApiResponse.<FoodStepResponse>builder()
                .code(200)
                .message("Cập nhật hình ảnh các bước thành công")
                .data(data)
                .build());
    }

    @GetMapping("/{foodId}/steps")
    public ResponseEntity<ApiResponse<List<StepResponse>>> viewStepByFood(@PathVariable("foodId") String id) {
        var data = stepService.viewStepsByFoodId(id);
        return ResponseEntity.ok(ApiResponse.<List<StepResponse>>builder()
                .code(200)
                .message("Thông tin các bước nấu ăn của món")
                .data(data)
                .build());
    }
}
