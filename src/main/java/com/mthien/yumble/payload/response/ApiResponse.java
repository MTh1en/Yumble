package com.mthien.yumble.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    @Builder.Default
    private int code = 1000;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private T data;
}
