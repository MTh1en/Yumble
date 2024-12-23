package com.mthien.yumble.mapper;

import com.mthien.yumble.entity.Premium;
import com.mthien.yumble.payload.request.premium.CreatePremiumRequest;
import com.mthien.yumble.payload.response.premium.PremiumResponse;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PremiumMapper {
    @Mappings({
            @Mapping(target = "start", expression = "java(defaultStart())"),
            @Mapping(target = "end", expression = "java(defaultEnd(createPremiumRequest))"),
            @Mapping(target = "remaining", expression = "java(defaultRemaining(createPremiumRequest))")
    })
    Premium createPremium(CreatePremiumRequest createPremiumRequest);

    @Mapping(target = "userId", expression = "java(getUserId(premium))")
    PremiumResponse toPremiumResponse(Premium premium);

    default LocalDateTime defaultStart() {
        return LocalDateTime.now();
    }

    default String getUserId(Premium premium) {
        return premium.getUsers().getId();
    }

    default LocalDateTime defaultEnd(CreatePremiumRequest createPremiumRequest) {
        return LocalDateTime.now().plusDays(createPremiumRequest.getDays());
    }

    default Long defaultRemaining(CreatePremiumRequest createPremiumRequest) {
        return createPremiumRequest.getDays();
    }
}
