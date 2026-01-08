package com.kangsol.extension_policy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 고정 확장자 토글 요청 DTO
@AllArgsConstructor
@Getter
public class FixedExtensionToggleRequest {

    @NotNull(message  = "block 값은 필수입니다.")
    private Boolean blocked;

    // Jackson 역직렬화를 위한 기본 생성자
    public FixedExtensionToggleRequest(){}

}
