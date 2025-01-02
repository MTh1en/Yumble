package com.mthien.yumble.controller;

import com.mthien.yumble.payload.request.step.CreateStepRequest;
import com.mthien.yumble.payload.request.step.UpdateStepRequest;
import com.mthien.yumble.payload.response.ApiResponse;
import com.mthien.yumble.payload.response.step.StepResponse;
import com.mthien.yumble.service.StepService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("step")
public class StepController {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping(value = "add-step-to-food/{foodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StepResponse>> addStepToFood(@PathVariable("foodId") String id,
                                                                   @ModelAttribute CreateStepRequest request) throws IOException {
        var data = stepService.addStepToFood(id, request);
        return ResponseEntity.ok(ApiResponse.<StepResponse>builder()
                .code(200)
                .message("Đã thêm bước vào món ăn thành công")
                .data(data)
                .build());
    }

    @PutMapping(value = "edit-step/{stepId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StepResponse>> editStep(@PathVariable("stepId") String id,
                                                              @ModelAttribute UpdateStepRequest request) throws IOException {
        var data = stepService.updateStep(id, request);
        return ResponseEntity.ok(ApiResponse.<StepResponse>builder()
                .code(200)
                .message("Cập nhật thông tin các bước thành công")
                .data(data)
                .build());
    }

    @GetMapping("view-by-food/{foodId}")
    public ResponseEntity<ApiResponse<Set<StepResponse>>> viewStepByFood(@PathVariable("foodId") String id) {
        var data = stepService.viewStepsByFoodId(id);
        return ResponseEntity.ok(ApiResponse.<Set<StepResponse>>builder()
                .code(200)
                .message("Thông tin các bước nấu ăn của món")
                .data(data)
                .build());
    }
}
