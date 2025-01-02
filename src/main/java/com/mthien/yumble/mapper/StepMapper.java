package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Step;
import com.mthien.yumble.payload.request.step.CreateStepRequest;
import com.mthien.yumble.payload.request.step.UpdateStepRequest;
import com.mthien.yumble.payload.response.step.StepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface StepMapper {
    @Mapping(target = "image", ignore = true)
    Step createStep(CreateStepRequest request);

    @Mapping(target = "image", ignore = true)
    void updateStep(@MappingTarget Step step, UpdateStepRequest request);

    @Mapping(target = "foodId", expression = "java(getFoodId(step))" )
    StepResponse toStepResponse(Step step);

    Set<StepResponse> toStepResponseList(Set<Step> steps);

    default String getFoodId(Step step){
        return step.getFood().getId();
    }
}
