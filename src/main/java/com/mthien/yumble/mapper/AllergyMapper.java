package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Allergy;
import com.mthien.yumble.payload.request.allergy.CreateAllergyRequest;
import com.mthien.yumble.payload.request.allergy.UpdateAllergyRequest;
import com.mthien.yumble.payload.response.allergy.AllergyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AllergyMapper {
    Allergy createAllergy(CreateAllergyRequest request);

    void updateAllergy(@MappingTarget Allergy allergy, UpdateAllergyRequest request);

    AllergyResponse toAllergyResponse(Allergy allergy);

    List<AllergyResponse> toListAllergyResponse(List<Allergy> allergyList);
}
