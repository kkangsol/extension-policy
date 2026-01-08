package com.kangsol.extension_policy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomExtensionCreateRequest {

    @NotBlank(message = "확장자 이름은 필수입니다.")
    private String ext;

    // Jackson 역직렬화를 위한 기본 생성자
    public CustomExtensionCreateRequest() {}
}
