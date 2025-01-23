package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.CookingMethod;
import com.mthien.yumble.payload.request.cookingmethod.CreateCookingMethodRequest;
import com.mthien.yumble.payload.request.cookingmethod.UpdateCookingMethodRequest;
import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CookingMethodMapper {
    CookingMethod createMethodCooking(CreateCookingMethodRequest request);

    void updateMethodCooking(@MappingTarget CookingMethod cookingMethod, UpdateCookingMethodRequest request);

    CookingMethodResponse toMethodCookingResponse(CookingMethod cookingMethod);

    List<CookingMethodResponse> toListMethodCookingResponse(List<CookingMethod> cookingMethodList);
}
