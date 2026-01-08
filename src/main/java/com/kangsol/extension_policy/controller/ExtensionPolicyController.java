package com.kangsol.extension_policy.controller;

import com.kangsol.extension_policy.dto.CustomExtensionCreateRequest;
import com.kangsol.extension_policy.dto.ExtensionResponse;
import com.kangsol.extension_policy.dto.FixedExtensionToggleRequest;
import com.kangsol.extension_policy.entity.ExtensionPolicy;
import com.kangsol.extension_policy.entity.ExtensionType;
import com.kangsol.extension_policy.service.ExtensionPolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/extensions")
@AllArgsConstructor
public class ExtensionPolicyController {

    private final ExtensionPolicyService extensionPolicyService;

    // 고정 확장자 토글
    @Operation(
            summary = "고정 확장자 차단 토글",
            description = "고정 확장자의 차단 여부를 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패"),
            @ApiResponse(responseCode = "404", description = "확장자 없음"),
            @ApiResponse(responseCode = "409", description = "고정 확장자가 아님")
    })
    @PatchMapping("/fixed/{ext}")
    public ResponseEntity<ExtensionResponse> toggleFixedExtension(
            @PathVariable String ext,
            @Valid @RequestBody FixedExtensionToggleRequest request
    ){
        ExtensionResponse response = extensionPolicyService.toggleFixed(ext, request);
        return ResponseEntity.ok(response);
    }


    // 커스텀 확장자 추가
    @Operation(
            summary = "커스텀 확장자 추가",
            description = "커스텀 확장자를 추가합니다. 최대 200개까지 허용됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "추가 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패"),
            @ApiResponse(responseCode = "409", description = "중복 또는 200개 제한 초과")
    })
    @PostMapping("/custom")
    public ResponseEntity<ExtensionPolicy> addCustom(
            @Valid @RequestBody CustomExtensionCreateRequest request
    ){
        ExtensionPolicy saved = extensionPolicyService.addCustomExtension(request);

        return ResponseEntity.created(URI.create("/extensions/custom/" + saved.getId()))
                .body(saved);
    }


    // 커스텀 확장자 삭제
    @Operation(
            summary = "커스텀 확장자 삭제",
            description = "ID 기반으로 커스텀 확장자를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "확장자 없음"),
            @ApiResponse(responseCode = "409", description = "고정 확장자 삭제 시도")
    })
    @DeleteMapping("/custom/{id}")
    public ResponseEntity<Void> deleteCustom(@PathVariable Long id){
        extensionPolicyService.deleteCustomExtension(id);
        return ResponseEntity.noContent().build();
    }


    // 차단확장자 조회
    @Operation(
            summary = "확장자 정책 조회",
            description = "확장자 정책 목록을 조회합니다. type(FIXED/CUSTOM) 필터를 지원합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 type 값")
    })
    @GetMapping
    public ResponseEntity<List<ExtensionPolicy>> getExtensions(
            @RequestParam(required = false)ExtensionType type
    ){
        return ResponseEntity.ok(extensionPolicyService.getExtensions(type));
    }
}
