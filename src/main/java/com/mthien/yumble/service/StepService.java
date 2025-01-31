package com.mthien.yumble.service;

import com.mthien.yumble.entity.Food;
import com.mthien.yumble.entity.Step;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.StepMapper;
import com.mthien.yumble.payload.request.step.CreateStepRequest;
import com.mthien.yumble.payload.request.step.UpdateStepRequest;
import com.mthien.yumble.payload.response.step.StepResponse;
import com.mthien.yumble.repository.FoodRepo;
import com.mthien.yumble.repository.StepRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Service
public class StepService {
    private final StepRepo stepRepo;
    private final StepMapper stepMapper;
    private final FoodRepo foodRepo;
    private final FirebaseService firebaseService;

    public StepService(StepRepo stepRepo, StepMapper stepMapper, FoodRepo foodRepo, FirebaseService firebaseService) {
        this.stepRepo = stepRepo;
        this.stepMapper = stepMapper;
        this.foodRepo = foodRepo;
        this.firebaseService = firebaseService;
    }

    public StepResponse addStepToFood(String foodId, CreateStepRequest request) throws IOException {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_FOUND));
        String fileName = String.format("%s-step%s", foodId, request.getStepOrder());
        String imageUrl = firebaseService.uploadFile(fileName, request.getImage());
        Step step = stepMapper.createStep(request);
        step.setFood(food);
        step.setImage(imageUrl);
        return stepMapper.toStepResponse(stepRepo.save(step));
    }

    public StepResponse updateStep(String stepId, UpdateStepRequest request) throws IOException {
        Step step = stepRepo.findById(stepId).orElseThrow(() -> new AppException(ErrorCode.STEP_NOT_FOUND));
        String fileName = String.format("%s-step%s", step.getFood().getId(), request.getStepOrder());
        String imageUrl = firebaseService.uploadFile(fileName, request.getImage());

        stepMapper.updateStep(step, request);
        step.setImage(imageUrl);
        return stepMapper.toStepResponse(stepRepo.save(step));
    }

    public Set<StepResponse> viewStepsByFoodId(String foodId) {
        Set<Step> steps = stepRepo.findByFoodIdOrderByStepOrder(foodId);
        return stepMapper.toSetStepResponse(steps);
    }

    public String generateUniqueStepImageUrl(Step step){
        String uniqueId = UUID.randomUUID().toString();
        return String.format("step/%s_%s_%s", uniqueId, step.getStepOrder(), step.getStepOrder());
    }
}
