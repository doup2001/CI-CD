package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.MemberDto;
import com.eedo.gdg.domain.entity.Address;
import com.eedo.gdg.domain.entity.Member;
import com.eedo.gdg.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;


    @Override
    public Long register(MemberDto dto) {
        Member result = dtoToEntity(dto);
        Member member = memberRepository.save(result);

        return member.getId();
    }

    @Override
    public MemberDto find(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        Member member = byId.orElseThrow();

        return entityToDTO(member);
    }


    @Override
    public void delete(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        Member member = byId.orElseThrow();

        memberRepository.delete(member);
    }

    @Override
    public Member update(MemberDto dto) {
        Optional<Member> byId = memberRepository.findById(dto.getId());
        Member member = byId.orElseThrow();

        // 트랜잭션에 의해서 처리?

        member.update(dto.getName(),
                Address.builder()
                .city(dto.getCity())
                .street_address(dto.getStreet_address())
                .zipcode(dto.getZipcode()).build());

        return member;

    }
}
