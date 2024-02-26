package com.gowine.controller;

import com.gowine.dto.MemberFormDto;
import com.gowine.entity.Member;
import com.gowine.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/joinForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, Model model) {
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);

        }

        catch (IllegalStateException e) {
            //model.addAttribute("errorMessage", e.getMessage());
            return "member/joinForm";
        }

        return "redirect:/";
    }

    // 로그인
    @GetMapping(value = "/login")
    public String loginMember() {
        return "member/loginForm";
    }

    // 로그인 에러
    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/loginForm";
    }

}
