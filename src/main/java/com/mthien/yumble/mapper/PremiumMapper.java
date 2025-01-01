package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Premium;
import com.mthien.yumble.payload.response.premium.PremiumResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PremiumMapper {
    PremiumResponse toPremiumResponse(Premium premium);
}
