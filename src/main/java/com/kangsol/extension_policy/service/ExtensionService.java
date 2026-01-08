package com.kangsol.extension_policy.service;


import com.kangsol.extension_policy.dto.CustomExtensionCreateRequest;
import com.kangsol.extension_policy.dto.ExtensionResponse;
import com.kangsol.extension_policy.dto.FixedExtensionToggleRequest;
import com.kangsol.extension_policy.entity.ExtensionPolicy;
import com.kangsol.extension_policy.entity.ExtensionType;
import com.kangsol.extension_policy.repository.ExtensionRepository;
import com.kangsol.extension_policy.util.ExtensionNormalizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ExtensionService {

    private final ExtensionRepository extensionRepository;

    // 고정확장자 토글
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


    // 커스텀 확장자 추가
    public ExtensionPolicy addCustomExtension(CustomExtensionCreateRequest request){
        String normalizedExt = ExtensionNormalizer.normalizeSingleExtension(request.getExt());

        if(extensionRepository.existsByExt(normalizedExt)){
            throw new IllegalArgumentException("이미 차단한 확장자입니다.");
        }

        ExtensionPolicy policy = ExtensionPolicy.custom(normalizedExt);
        return extensionRepository.save(policy);
    }


    // 커스텀 확장자 삭제
    public void deleteCustomExtension(Long id){
        ExtensionPolicy policy = extensionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 확장자를 찾을 수 없습니다."));
        if(policy.getType() != ExtensionType.CUSTOM){
            throw new IllegalArgumentException("고정 확장자는 삭제할 수 없습니다.");
        }

        extensionRepository.delete(policy);
    }


    // 조회 메서드
    public List<ExtensionPolicy> getExtensions(ExtensionType type){
        if(type == null){
            return extensionRepository.findAllByOrderByTypeAscExtAsc();
        }
        return extensionRepository.findAllByTypeOrderByExtAsc(type);
    }

}
