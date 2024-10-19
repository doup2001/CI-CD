package com.eedo.gdg.domain.controller;

import com.eedo.gdg.domain.dto.MemberDto;
import com.eedo.gdg.domain.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    MemberController memberController;

    @Mock
    MemberService memberService;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("회원가입")
    void Register() throws Exception {

        // given
        MemberDto dto = MemberDto.builder()
                .name("mockito테스트")
                .city("성남시")
                .street_address("장미로55")
                .zipcode(13441)
                .build();

        //when
        ResultActions actions = mockMvc.perform(post("/api/member/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))

        //then
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    @DisplayName("멤버 조회")
    public void find() throws Exception{
        //given

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .name("테스트")
                .city("성남")
                .street_address("테스트")
                .zipcode(10001)
                .build();

        Long register = memberService.register(memberDto);

        // memberService.find()가 memberDto를 반환하도록 설정
        Mockito.doReturn(memberDto).when(memberService).find(register);

        mockMvc.perform(get("/api/member/find/{id}", register))

                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Result.name").value("테스트"))
                .andExpect(jsonPath("$.Result.city").value("성남"))
                .andExpect(jsonPath("$.Result.street_address").value("테스트"))
                .andExpect(jsonPath("$.Result.zipcode").value(10001))
                .andDo(print());

    }

}

