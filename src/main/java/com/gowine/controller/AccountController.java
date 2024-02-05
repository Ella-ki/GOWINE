package com.gowine.controller;

import com.gowine.entity.Member;
import com.gowine.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
//@RequestMapping("/members")
@RequiredArgsConstructor
public class AccountController {
    private final MemberService memberService;

    @PostMapping(value = "/login/checkid")
    public ResponseEntity<String> checkEmailDuplication(@RequestParam("id") String id) {
        //log.info(String.valueOf(memberService.isEmailExist(id)));

        if (memberService.isEmailExist(id)) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.ok("EXIST");
        }
    }
}
