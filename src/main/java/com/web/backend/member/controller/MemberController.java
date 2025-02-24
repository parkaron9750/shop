package com.web.backend.member.controller;

import com.web.backend.member.dto.MemberDto;
import com.web.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String createMember(@ModelAttribute("member") MemberDto memberDto) {
        return "member/memberForm";

    }

    @PostMapping("/new")
    public String memberSubmit(@ModelAttribute("member") MemberDto memberDto) {
        log.info("memberDto: {}", memberDto);
        return "redirect:/";
    }
}
