package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.MemberDto;
import com.eedo.gdg.domain.entity.Address;
import com.eedo.gdg.domain.entity.Member;

public interface MemberService {

    // 회원 가입 서비스
    Long register(MemberDto dto);

    // Id로 Member 찾는 서비스
    MemberDto find(Long id);

    // 회원 탈퇴 서비스
    void delete(Long id);

    // 멤버 수정 서비스
    Member update(MemberDto dto);

    // ModelMapper 대신 메서드 추가
    default MemberDto entityToDTO(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .city(member.getAddress().getCity())
                .street_address(member.getAddress().getStreet_address())
                .zipcode(member.getAddress().getZipcode())
                .build();
    }

    default Member dtoToEntity(MemberDto dto) {
        return Member.builder()
                .name(dto.getName())
                .address(Address.builder()
                        .city(dto.getCity())
                        .street_address(dto.getStreet_address())
                        .zipcode(dto.getZipcode()).build())
                .build();
    }


}
