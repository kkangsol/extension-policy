package com.kangsol.extension_policy.service;


import com.kangsol.extension_policy.dto.ExtensionResponse;
import com.kangsol.extension_policy.dto.FixedExtensionToggleRequest;
import com.kangsol.extension_policy.entity.ExtensionPolicy;
import com.kangsol.extension_policy.entity.ExtensionType;
import com.kangsol.extension_policy.repository.ExtensionRepository;
import com.kangsol.extension_policy.util.ExtensionNormalizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ExtensionService {

    private final ExtensionRepository extensionRepository;

    @Transactional
    public ExtensionResponse toggleFixed(String rawExt, FixedExtensionToggleRequest request){

        // 정규화
        String ext = ExtensionNormalizer.normalizeSingleExtension(rawExt);

        // 조회
        ExtensionPolicy extensionPolicy = extensionRepository.findByExt(ext)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 확장자입니다."));

        // 타입 검증
        if(extensionPolicy.getType() != ExtensionType.FIXED){
            throw new IllegalArgumentException("고정 확장자만 선택 가능합니다.");
        }

        // 상태 변경
        extensionPolicy.changeBlocked(request.getBlocked());

        return ExtensionResponse.fromEntity(extensionPolicy);
    }
}
