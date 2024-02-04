package com.gowine.controller;

import com.gowine.entity.Member;
import com.gowine.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
//@RequestMapping("/members")
@RequiredArgsConstructor
public class AccountController {
    private final MemberService memberService;

    @PostMapping(value = "/login/checkid")
    public @ResponseBody String existMember(Member member, @RequestBody String id){

        log.info(memberService.validateDuplicateMember(member));

        if(memberService.validateDuplicateMember(member) == "1") {
            return "1";
        }

        return "0";
    }
}
