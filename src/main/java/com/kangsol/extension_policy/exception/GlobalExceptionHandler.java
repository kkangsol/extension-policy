package com.kangsol.extension_policy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class GlobalExceptionHandler {

    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException e){
        String msg = "type 값이 올바르지 않습니다. (FIXED/CUSTOM)";
        return ResponseEntity.badRequest().body(msg);
    }
}
