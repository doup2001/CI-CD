package com.eedo.gdg.domain.controller;

import com.eedo.gdg.domain.dto.MemberDto;
import com.eedo.gdg.domain.entity.Member;
import com.eedo.gdg.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public String hi() {
        return " Member Hello";
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody MemberDto dto) {
        Long register = memberService.register(dto);
        return Map.of("Success", register);
    }

    @GetMapping("/find/{id}")
    public Map<String, Object> find(@PathVariable Long id) {
        MemberDto memberDto = memberService.find(id);
        return Map.of("Result", memberDto);
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> update(@RequestBody MemberDto dto) {
        Member update = memberService.update(dto);
        MemberDto result = memberService.entityToDTO(update);
        return Map.of("Result", result);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        memberService.delete(id);
        return Map.of("Delete", id);
    }
}
