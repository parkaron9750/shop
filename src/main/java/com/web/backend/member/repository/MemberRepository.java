package com.web.backend.member.repository;

import com.web.backend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
}
