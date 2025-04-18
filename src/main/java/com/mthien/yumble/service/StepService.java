package com.mthien.yumble.service;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Step;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.StepMapper;
import com.mthien.yumble.payload.request.step.CreateStepRequest;
import com.mthien.yumble.payload.request.step.UpdateStepRequest;
import com.mthien.yumble.payload.response.step.StepDetailResponse;
import com.mthien.yumble.payload.response.step.StepResponse;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.repository.StepRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StepService {
    private final StepRepo stepRepo;
    private final StepMapper stepMapper;
    private final FoodRepo foodRepo;
    private final FirebaseService firebaseService;


    public StepDetailResponse addStepToFood(String foodId, CreateStepRequest request) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        Step step = stepMapper.createStep(request);
        step.setFood(food);
        step.setImage(firebaseService.uploadFile(generateUniqueStepImageUrl(food, step), request.getImage()));
        return stepMapper.toFoodStepResponse(stepRepo.save(step));
    }

    public StepDetailResponse updateStepInformation(String stepId, UpdateStepRequest request) {
        Step step = stepRepo.findById(stepId).orElseThrow(() -> new AppException(ErrorCode.STEP_NOT_FOUND));
        stepMapper.updateStep(step, request);
        return stepMapper.toFoodStepResponse(stepRepo.save(step));
    }

    public StepDetailResponse updateStepImage(String stepId, MultipartFile image) {
        Step step = stepRepo.findById(stepId).orElseThrow(() -> new AppException(ErrorCode.STEP_NOT_FOUND));
        step.setImage(firebaseService.uploadFile(generateUniqueStepImageUrl(step.getFood(), step), image));
        return stepMapper.toFoodStepResponse(stepRepo.save(step));
    }

    public List<StepResponse> viewStepsByFood(String foodId) {
        return stepRepo.findByFoodIdOrderByStepOrder(foodId).stream().map(step -> {
            String image = Optional.ofNullable(step.getImage())
                    .filter(imageUrl -> imageUrl.contains("step"))
                    .map(firebaseService::getImageUrl)
                    .orElse(step.getImage());
            return stepMapper.toStepResponse(image, step);
        }).collect(Collectors.toList());
    }

    public void deleteStep(String stepId) {
        Step step = stepRepo.findById(stepId).orElseThrow(() -> new AppException(ErrorCode.STEP_NOT_FOUND));
        stepRepo.delete(step);
    }

    public String generateUniqueStepImageUrl(Food food, Step step) {
        String uniqueId = UUID.randomUUID().toString();
        return String.format("step/%s_%s_%s", uniqueId, food.getId(), step.getStepOrder());
    }
}
