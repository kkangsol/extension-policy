package com.kangsol.extension_policy.config;

import com.kangsol.extension_policy.entity.ExtensionPolicy;
import com.kangsol.extension_policy.repository.ExtensionRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// 고정 확장자 초기 시딩
@Component
public class DataInitializer implements ApplicationRunner {

    private static final List<String> FIXED_EXTENSIONS =
            List.of("bat", "cmd", "com", "cpl", "exe", "scr", "js");

    private final ExtensionRepository extensionRepository;

    public DataInitializer(ExtensionRepository extensionRepository){
        this.extensionRepository = extensionRepository;
    }


    @Override
    public void run(ApplicationArguments args){
        for(String ext : FIXED_EXTENSIONS){
            // 이미 존재하면 스킵
            if(extensionRepository.existsByExt(ext)){
                continue;
            }
            extensionRepository.save(ExtensionPolicy.fixed(ext));
        }
    }
}
