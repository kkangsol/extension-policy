package com.kangsol.extension_policy.controller;

import com.kangsol.extension_policy.dto.ExtensionResponse;
import com.kangsol.extension_policy.dto.FixedExtensionToggleRequest;
import com.kangsol.extension_policy.service.ExtensionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extensions")
@AllArgsConstructor
public class ExtensionController {

    private final ExtensionService extensionService;

    @PatchMapping("/fixed/{ext}")
    public ResponseEntity<ExtensionResponse> toggleFixedExtension(
            @PathVariable String ext,
            @Valid @RequestBody FixedExtensionToggleRequest request
    ){
        ExtensionResponse response = extensionService.toggleFixed(ext, request);
        return ResponseEntity.ok(response);
    }
}
