package com.kangsol.extension_policy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private int status;
    private String path;
    private LocalDateTime timestamp;
}
