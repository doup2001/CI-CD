package com.eedo.gdg.domain.repository;

import com.eedo.gdg.domain.entity.Address;
import com.eedo.gdg.domain.entity.Member;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    
    @Test
    public void Register() throws Exception{

        //given
        Member testMember = Member.builder()
                .name("이도연")
                .address(
                        Address.builder()
                                .city("성남시")
                                .street_address("장미로55")
                                .zipcode(13441).build()
                ).build();

        //when

        Member saved_Member = memberRepository.save(testMember);

        //then
        assertThat(saved_Member.getName()).isEqualTo("이도연");
        log.info(testMember.getName());
    }

    @Test
    public void findById() throws Exception{
        //given
        Member testMember = Member.builder()
                .name("이도연")
                .address(
                        Address.builder()
                                .city("성남시")
                                .street_address("장미로55")
                                .zipcode(13441).build()
                ).build();

        //when
        Member saved = memberRepository.save(testMember);
        Long id = saved.getId();

        //then
        Optional<Member> byId = memberRepository.findById(id);
        Member member = byId.orElseThrow();

        Assertions.assertThat(member.getName()).isEqualTo("이도연");
        Assertions.assertThat(member.getAddress().getCity()).isEqualTo("성남시");
        Assertions.assertThat(member.getAddress().getStreet_address()).isEqualTo("장미로55");
        Assertions.assertThat(member.getAddress().getZipcode()).isEqualTo(13441);
    }
}