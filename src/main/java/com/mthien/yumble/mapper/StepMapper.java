package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Step;
import com.mthien.yumble.payload.request.step.CreateStepRequest;
import com.mthien.yumble.payload.request.step.UpdateStepRequest;
import com.mthien.yumble.payload.response.step.StepDetailResponse;
import com.mthien.yumble.payload.response.step.StepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface StepMapper {
    @Mapping(target = "food", ignore = true)
    @Mapping(target = "image", ignore = true)
    Step createStep(CreateStepRequest request);

    @Mapping(target = "image", ignore = true)
    void updateStep(@MappingTarget Step step, UpdateStepRequest request);

    @Mapping(target = "image", expression = "java(image)")
    StepResponse toStepResponse(String image, Step step);

    StepDetailResponse toFoodStepResponse(Step step);
}
