package com.kangsol.extension_policy.dto;

import com.kangsol.extension_policy.entity.ExtensionPolicy;
import com.kangsol.extension_policy.entity.ExtensionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExtensionResponse {

    private final String ext;
    private final ExtensionType type;
    private final boolean blocked;

    public static ExtensionResponse fromEntity(ExtensionPolicy extensionPolicy){
        return new ExtensionResponse(extensionPolicy.getExt(), extensionPolicy.getType(), extensionPolicy.isBlocked());
    }


}
