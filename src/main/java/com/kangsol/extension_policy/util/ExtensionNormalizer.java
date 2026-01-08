package com.kangsol.extension_policy.util;

import org.springframework.util.StringUtils;

public final class ExtensionNormalizer {

    private ExtensionNormalizer(){}

    public static String normalizeSingleExtension(String raw){

        // null이거나 빈문자열이거나 공백만 있을 경우
        if(!StringUtils.hasText(raw)){
            throw new IllegalArgumentException("확장자는 비어있을 수 없습니다.");
        }

        // 앞뒤 공백 제거
        String ext = raw.trim();

        // 맨 앞 .제거(여러 개일 경우도 제거)
        while(ext.startsWith(".")){
            ext = ext.substring(1);
        }

        // 소문자 변환
        ext = ext.toLowerCase();

        // 정규화 이후 빈 문자열 방지
        if(!StringUtils.hasText(ext)){
            throw new IllegalArgumentException("확장자는 비어있을 수 없습니다.");
        }

        // 중간의 . 거절
        if(ext.contains(".")){
            throw new IllegalArgumentException("중간의 .을 제거해주세요. 복합 확장자는 지원하지 않습니다.");
        }

        // 길이제하
        if(ext.length() > 20){
            throw new IllegalArgumentException("확장자는 최대 20자까지 허용됩니다.");
        }

        if(!ext.matches("^[a-z0-9]{1,20}$")){
            throw new IllegalArgumentException("확장자는 영소문자/숫자만 허용됩니다.");
        }

        return ext;
    }
}
