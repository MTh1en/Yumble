package com.mthien.yumble.service;

import com.mthien.yumble.entity.CookingMethod;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.CookingMethodMapper;
import com.mthien.yumble.payload.request.cookingmethod.CreateCookingMethodRequest;
import com.mthien.yumble.payload.request.cookingmethod.UpdateCookingMethodRequest;
import com.mthien.yumble.payload.response.cookingmethod.CookingMethodResponse;
import com.mthien.yumble.repository.CookingMethodRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookingMethodService {
    private final CookingMethodRepo cookingMethodRepo;
    private final CookingMethodMapper cookingMethodMapper;

    public CookingMethodService(CookingMethodRepo cookingMethodRepo, CookingMethodMapper cookingMethodMapper) {
        this.cookingMethodRepo = cookingMethodRepo;
        this.cookingMethodMapper = cookingMethodMapper;
    }

    public CookingMethodResponse create(CreateCookingMethodRequest request) {
        CookingMethod cookingMethod = cookingMethodMapper.createMethodCooking(request);
        return cookingMethodMapper.toMethodCookingResponse(cookingMethodRepo.save(cookingMethod));
    }

    public CookingMethodResponse update(String id, UpdateCookingMethodRequest request) {
        CookingMethod cookingMethod = cookingMethodRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        cookingMethodMapper.updateMethodCooking(cookingMethod, request);
        return cookingMethodMapper.toMethodCookingResponse(cookingMethodRepo.save(cookingMethod));
    }

    public CookingMethodResponse viewOne(String id) {
        CookingMethod cookingMethod = cookingMethodRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        return cookingMethodMapper.toMethodCookingResponse(cookingMethod);
    }

    public List<CookingMethodResponse> viewAll() {
        List<CookingMethod> cookingMethodList = cookingMethodRepo.findAll();
        return cookingMethodMapper.toListMethodCookingResponse(cookingMethodList);
    }

    public void delete(String id) {
        CookingMethod cookingMethod = cookingMethodRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_COOKING_NOT_FOUND));
        cookingMethodRepo.delete(cookingMethod);
    }
}
