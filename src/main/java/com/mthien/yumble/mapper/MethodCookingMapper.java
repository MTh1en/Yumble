package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.MethodCooking;
import com.mthien.yumble.payload.request.cookingmethod.CreateCookingMethodRequest;
import com.mthien.yumble.payload.request.cookingmethod.UpdateCookingMethodRequest;
import com.mthien.yumble.payload.response.methodcooking.MethodCookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MethodCookingMapper {
    MethodCooking createMethodCooking(CreateCookingMethodRequest request);

    void updateMethodCooking(@MappingTarget MethodCooking methodCooking, UpdateCookingMethodRequest request);

    MethodCookingResponse toMethodCookingResponse(MethodCooking methodCooking);

    List<MethodCookingResponse> toListMethodCookingResponse(List<MethodCooking> methodCookingList);
}
