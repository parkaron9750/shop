package com.web.backend.member.service;

import com.web.backend.member.entity.Member;
import com.web.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateMember(member);
        return memberRepository.save(member);
    }

    private void validateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {throw new IllegalStateException("이미 등록된 사용자입니다.");}
    }
}
