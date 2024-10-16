package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.MemberDto;
import com.eedo.gdg.domain.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Log4j2
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void registerAndFind() throws Exception{
        //given
        MemberDto dto = MemberDto.builder()
                .name("DTO생성")
                .city("성남시")
                .street_address("장미로55")
                .zipcode(13441)
                .build();
        //when
        Long registerID = memberService.register(dto);
        MemberDto result = memberService.find(registerID);

        //then
        assertThat(result.getName()).isEqualTo("DTO생성");
        log.info("result :"+ result);
    }

    @Test
    public void update() throws Exception{

        //given
        MemberDto dto = MemberDto.builder()
                .name("생성")
                .city("성남시")
                .street_address("장미로55")
                .zipcode(13441)
                .build();

        Long registerID = memberService.register(dto);
        MemberDto memberDto = memberService.find(registerID);

        log.info(memberDto.getId());
        log.info(memberDto.getName());

        MemberDto changeDto = MemberDto.builder()
                .id(registerID)
                .name("값_변경")
                .city("서울시")
                .street_address("서울로11")
                .zipcode(11111)
                .build();

        //when
        Member updateMember = memberService.update(changeDto);

        //then
        Assertions.assertThat(updateMember.getName()).isEqualTo("값_변경");
        log.info(updateMember.getId());
        log.info(updateMember.getName());
    }

    @Test
    public void delete() throws Exception{
        //given
        MemberDto dto = MemberDto.builder()
                .name("삭제_테스트")
                .city("성남시")
                .street_address("장미로55")
                .zipcode(13441)
                .build();

        Long register = memberService.register(dto);
        log.info(register);

        //when
        memberService.delete(register);

        //then
        Throwable exception = assertThrows(NoSuchElementException.class, () -> {
            memberService.find(register);
        });

        assertEquals("No value present", exception.getMessage());
        log.info(exception.getMessage());
    }

}