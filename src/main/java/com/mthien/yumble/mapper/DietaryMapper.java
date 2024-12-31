package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.payload.request.dietary.CreateDietaryRequest;
import com.mthien.yumble.payload.request.dietary.UpdateDietaryRequest;
import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DietaryMapper {
    Dietary createDietary(CreateDietaryRequest request);

    void updateDietary(@MappingTarget Dietary dietary, UpdateDietaryRequest request);

    DietaryResponse toDietaryResponse(Dietary dietary);

    List<DietaryResponse> toListDietaryResponse(List<Dietary> dietaryList);
}
