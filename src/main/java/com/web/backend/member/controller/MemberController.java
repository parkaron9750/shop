package com.web.backend.member.controller;

import com.web.backend.member.dto.MemberDto;
import com.web.backend.member.entity.Member;
import com.web.backend.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "member/memberLogin";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/";
    }


    @GetMapping("/new")
    public String createPage(@ModelAttribute("member") MemberDto memberDto) {
        return "member/memberForm";

    }

    @GetMapping("/login/error")
    public String loginErrorPage(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
        return "member/memberLogin";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("member") MemberDto memberDto
                            , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/memberLogin";
        }
        return "redirect:/";
    }


    @PostMapping("/new")
    public String createSubmit(@Valid @ModelAttribute("member") MemberDto memberDto
                               , BindingResult error, Model model) {

        //error 가 발생시 form 으로 되돌린다.
        if (error.hasErrors()) {
            return "member/memberForm";
        }

        try{
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("error", e.getMessage());
            //저장이 안되기 때문에 전에 있던 값들을 필드에 그대로 나둔다.
            return "member/memberForm";
        }
        return "redirect:/";
    }
}
