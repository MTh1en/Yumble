package com.mthien.yumble.service;

import com.mthien.yumble.entity.Dietary;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.DietaryMapper;
import com.mthien.yumble.payload.request.dietary.CreateDietaryRequest;
import com.mthien.yumble.payload.request.dietary.UpdateDietaryRequest;
import com.mthien.yumble.payload.response.dietary.DietaryResponse;
import com.mthien.yumble.repository.DietaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietaryService {
    private final DietaryRepo dietaryRepo;
    private final DietaryMapper dietaryMapper;

    public DietaryResponse create(CreateDietaryRequest request) {
        Dietary dietary = dietaryMapper.createDietary(request);
        return dietaryMapper.toDietaryResponse(dietaryRepo.save(dietary));
    }

    public DietaryResponse update(String id, UpdateDietaryRequest request) {
        Dietary dietary = dietaryRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.DIETARY_NOT_FOUND));
        dietaryMapper.updateDietary(dietary, request);
        return dietaryMapper.toDietaryResponse(dietaryRepo.save(dietary));
    }

    public DietaryResponse viewOne(String id) {
        Dietary dietary = dietaryRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.DIETARY_NOT_FOUND));
        return dietaryMapper.toDietaryResponse(dietary);
    }

    public List<DietaryResponse> viewAll() {
        List<Dietary> dietaryList = dietaryRepo.findAll();
        return dietaryMapper.toListDietaryResponse(dietaryList);
    }

    public void delete(String id) {
        Dietary dietary = dietaryRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.DIETARY_NOT_FOUND));
        dietaryRepo.delete(dietary);
    }
}
