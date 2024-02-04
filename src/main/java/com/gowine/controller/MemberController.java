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

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    
    // 마이페이지
    @GetMapping(value = "/my")
    public String myPage() {
        return "member/myPage";
    }

    // 찜 상품
    @GetMapping(value = "/favorite")
    public String myFavoriteItem() {
        return "member/myFavorite";
    }
}
