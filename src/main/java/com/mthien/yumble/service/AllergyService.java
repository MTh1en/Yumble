package com.mthien.yumble.service;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.AllergyMapper;
import com.mthien.yumble.payload.request.allergy.CreateAllergyRequest;
import com.mthien.yumble.payload.request.allergy.UpdateAllergyRequest;
import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import com.mthien.yumble.repository.AllergyRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllergyService {
    private final AllergyMapper allergyMapper;
    private final AllergyRepo allergyRepo;

    public AllergyService(AllergyMapper allergyMapper, AllergyRepo allergyRepo) {
        this.allergyMapper = allergyMapper;
        this.allergyRepo = allergyRepo;
    }

    public AllergyResponse create(CreateAllergyRequest request) {
        Allergy allergy = allergyMapper.createAllergy(request);
        return allergyMapper.toAllergyResponse(allergyRepo.save(allergy));
    }

    public AllergyResponse update(String id, UpdateAllergyRequest request) {
        Allergy allergy = allergyRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        allergyMapper.updateAllergy(allergy, request);
        return allergyMapper.toAllergyResponse(allergyRepo.save(allergy));
    }

    public AllergyResponse viewOne(String id) {
        Allergy allergy = allergyRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        return allergyMapper.toAllergyResponse(allergy);
    }

    public List<AllergyResponse> viewAll() {
        List<Allergy> allergyList = allergyRepo.findAll();
        return allergyMapper.toListAllergyResponse(allergyList);
    }

    public void delete(String id) {
        Allergy allergy = allergyRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ALLERGY_NOT_FOUND));
        allergyRepo.delete(allergy);
    }
}
