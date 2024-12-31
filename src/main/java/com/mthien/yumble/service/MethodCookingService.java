package com.mthien.yumble.service;

import com.mthien.yumble.entity.MethodCooking;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.MethodCookingMapper;
import com.mthien.yumble.payload.request.cookingmethod.CreateCookingMethodRequest;
import com.mthien.yumble.payload.request.cookingmethod.UpdateCookingMethodRequest;
import com.mthien.yumble.payload.response.methodcooking.MethodCookingResponse;
import com.mthien.yumble.repository.MethodCookingRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MethodCookingService {
    private final MethodCookingRepo methodCookingRepo;
    private final MethodCookingMapper methodCookingMapper;

    public MethodCookingService(MethodCookingRepo methodCookingRepo, MethodCookingMapper methodCookingMapper) {
        this.methodCookingRepo = methodCookingRepo;
        this.methodCookingMapper = methodCookingMapper;
    }

    public MethodCookingResponse create(CreateCookingMethodRequest request) {
        MethodCooking methodCooking = methodCookingMapper.createMethodCooking(request);
        return methodCookingMapper.toMethodCookingResponse(methodCookingRepo.save(methodCooking));
    }

    public MethodCookingResponse update(String id, UpdateCookingMethodRequest request) {
        MethodCooking methodCooking = methodCookingRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        methodCookingMapper.updateMethodCooking(methodCooking, request);
        return methodCookingMapper.toMethodCookingResponse(methodCookingRepo.save(methodCooking));
    }

    public MethodCookingResponse viewOne(String id) {
        MethodCooking methodCooking = methodCookingRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        return methodCookingMapper.toMethodCookingResponse(methodCooking);
    }

    public List<MethodCookingResponse> viewAll() {
        List<MethodCooking> methodCookingList = methodCookingRepo.findAll();
        return methodCookingMapper.toListMethodCookingResponse(methodCookingList);
    }

    public void delete(String id) {
        MethodCooking methodCooking = methodCookingRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        methodCookingRepo.delete(methodCooking);
    }
}
