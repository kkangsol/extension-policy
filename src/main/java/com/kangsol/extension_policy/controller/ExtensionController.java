package com.kangsol.extension_policy.controller;

import com.kangsol.extension_policy.dto.CustomExtensionCreateRequest;
import com.kangsol.extension_policy.dto.ExtensionResponse;
import com.kangsol.extension_policy.dto.FixedExtensionToggleRequest;
import com.kangsol.extension_policy.entity.ExtensionPolicy;
import com.kangsol.extension_policy.entity.ExtensionType;
import com.kangsol.extension_policy.service.ExtensionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/extensions")
@AllArgsConstructor
public class ExtensionController {

    private final ExtensionService extensionService;

    // 고정 확장자 토글
    @PatchMapping("/fixed/{ext}")
    public ResponseEntity<ExtensionResponse> toggleFixedExtension(
            @PathVariable String ext,
            @Valid @RequestBody FixedExtensionToggleRequest request
    ){
        ExtensionResponse response = extensionService.toggleFixed(ext, request);
        return ResponseEntity.ok(response);
    }


    // 커스텀 확장자 추가
    @PostMapping("/custom")
    public ResponseEntity<ExtensionPolicy> addCustom(
            @Valid @RequestBody CustomExtensionCreateRequest request
    ){
        ExtensionPolicy saved = extensionService.addCustomExtension(request);

        return ResponseEntity.created(URI.create("/extensions/custom/" + saved.getId()))
                .body(saved);
    }


    // 커스텀 확장자 삭제
    @DeleteMapping("/custom/{id}")
    public ResponseEntity<Void> deleteCustom(@PathVariable Long id){
        extensionService.deleteCustomExtension(id);
        return ResponseEntity.noContent().build();
    }

    
    // 차단확장자 조회
    @GetMapping
    public ResponseEntity<List<ExtensionPolicy>> getExtensions(
            @RequestParam(required = false)ExtensionType type
    ){
        return ResponseEntity.ok(extensionService.getExtensions(type));
    }
}
