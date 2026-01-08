package com.kangsol.extension_policy.repository;

import com.kangsol.extension_policy.entity.ExtensionPolicy;
import com.kangsol.extension_policy.entity.ExtensionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtensionPolicyRepository extends JpaRepository<ExtensionPolicy, Long> {

    // ext로 조회
    Optional<ExtensionPolicy> findByExt(String ext);

    // ext 존재 여부 확인
    boolean existsByExt(String ext);

    // 커스텀확장자 총 갯수 제한에서 사용
    long countByType(ExtensionType type);

    // 전체 조회(타입, 확장자명 오름차순)
    List<ExtensionPolicy> findAllByOrderByTypeAscExtAsc();

    // 타입별 조회(정렬 포함)
    List<ExtensionPolicy> findAllByTypeOrderByExtAsc(ExtensionType type);
}
