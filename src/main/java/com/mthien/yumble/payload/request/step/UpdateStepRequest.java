package com.mthien.yumble.payload.request.step;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStepRequest {
    private MultipartFile image;
    private Integer stepOrder;
    private String description;
}
