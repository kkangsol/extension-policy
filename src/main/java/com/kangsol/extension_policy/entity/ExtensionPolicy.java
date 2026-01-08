package com.kangsol.extension_policy.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "extension_policy")
@Getter
public class ExtensionPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 확장자
    @Column(name = "ext", nullable = false, unique = true, length = 20)
    private String ext;

    // 확장자 타입(고정/커스텀)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private ExtensionType type;

    // 차단 여부 (true:차단 / false:차단해제)
    @Column(name = "blocked", nullable = false)
    private boolean blocked;

    protected ExtensionPolicy(){}

    // 외부 생성 방지
    private ExtensionPolicy(String ext, ExtensionType type, boolean blocked){
        this.ext = ext;
        this.type = type;
        this.blocked = blocked;
    }

    // 고정 확장자 초기 시딩용
    public static ExtensionPolicy fixed(String ext){
        return new ExtensionPolicy(ext, ExtensionType.FIXED, false);
    }

    // 커스텀 확장자 생성용
    public static ExtensionPolicy custom(String ext){
        return new ExtensionPolicy(ext, ExtensionType.CUSTOM, true);
    }

    // 차단 상태 변경
    public void changeBlocked(boolean blocked){
        this.blocked = blocked;
    }
}
