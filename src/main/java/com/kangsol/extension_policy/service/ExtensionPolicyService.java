package com.kangsol.extension_policy.service;


import com.kangsol.extension_policy.dto.CustomExtensionCreateRequest;
import com.kangsol.extension_policy.dto.ExtensionResponse;
import com.kangsol.extension_policy.dto.FixedExtensionToggleRequest;
import com.kangsol.extension_policy.entity.ExtensionPolicy;
import com.kangsol.extension_policy.entity.ExtensionType;
import com.kangsol.extension_policy.exception.BadRequestException;
import com.kangsol.extension_policy.exception.ConflictException;
import com.kangsol.extension_policy.exception.NotFoundException;
import com.kangsol.extension_policy.repository.ExtensionPolicyRepository;
import com.kangsol.extension_policy.util.ExtensionNormalizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ExtensionPolicyService {

    private final ExtensionPolicyRepository extensionPolicyRepository;
    private static final int CUSTOM_LIMIT = 200;

    // 고정확장자 토글
    public ExtensionResponse toggleFixed(String rawExt, FixedExtensionToggleRequest request){

        // 정규화
        final String ext;
        try {
            ext = ExtensionNormalizer.normalizeSingleExtension(rawExt);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }

        // 조회
        ExtensionPolicy extensionPolicy = extensionPolicyRepository.findByExt(ext)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 확장자입니다."));

        // 타입 검증
        if (extensionPolicy.getType() != ExtensionType.FIXED) {
            throw new ConflictException("고정 확장자만 차단 상태를 변경할 수 있습니다.");
        }

        // 상태 변경
        extensionPolicy.changeBlocked(request.getBlocked());

        return ExtensionResponse.fromEntity(extensionPolicy);
    }


    // 커스텀 확장자 추가
    public ExtensionPolicy addCustomExtension(CustomExtensionCreateRequest request){

        final String normalizedExt;
        try {
            normalizedExt = ExtensionNormalizer.normalizeSingleExtension(request.getExt());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }

        if (extensionPolicyRepository.existsByExt(normalizedExt)) {
            throw new ConflictException("이미 존재하는 확장자입니다.");
        }

        long customCount = extensionPolicyRepository.countByType(ExtensionType.CUSTOM);
        if (customCount >= CUSTOM_LIMIT) {
            throw new ConflictException("커스텀 확장자는 최대 200개까지만 등록할 수 있습니다.");
        }

        return extensionPolicyRepository.save(ExtensionPolicy.custom(normalizedExt));
    }


    // 커스텀 확장자 삭제
    public void deleteCustomExtension(Long id){
        ExtensionPolicy policy = extensionPolicyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("확장자 정책을 찾을 수 없습니다."));

        if (policy.getType() != ExtensionType.CUSTOM) {
            throw new ConflictException("고정 확장자는 삭제할 수 없습니다.");
        }

        extensionPolicyRepository.delete(policy);
    }


    // 조회 메서드
    public List<ExtensionPolicy> getExtensions(ExtensionType type){
        if(type == null){
            return extensionPolicyRepository.findAllByOrderByTypeAscExtAsc();
        }
        return extensionPolicyRepository.findAllByTypeOrderById(type);
    }

}
