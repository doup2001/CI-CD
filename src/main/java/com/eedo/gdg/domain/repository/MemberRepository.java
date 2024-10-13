package com.eedo.gdg.domain.repository;

import com.eedo.gdg.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
