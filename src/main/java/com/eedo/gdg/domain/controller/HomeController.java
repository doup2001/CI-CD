package com.eedo.gdg.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final Environment environment;

    @GetMapping("/")
    public String getVersion() {
        return "이 서버는 V1입니다. 빌드 테스트입니닷!!!";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(environment.getActiveProfiles()).findFirst().orElse("default");
    }
}
