package com.gowine.controller;

import com.gowine.config.LoginMember;
import com.gowine.entity.Member;
import com.gowine.repository.MemberRepository;
import com.gowine.service.LikeService;
import com.gowine.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/like/{itemId}")
    public ResponseEntity<List<String>> getLikeCount(@PathVariable Long itemId,
                                                     String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        log.info("itemId : {} ", itemId);
        log.info("loginMember : {} ", member);

        List<String> resultData = likeService.count(itemId, String.valueOf(member.getId()));

        log.info("likeCount : {} ", resultData);

        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @DeleteMapping(value = "/like/{itemId}")
    public ResponseEntity<String> cancelLike(@PathVariable Long itemId ,Principal principal) {
        String email = principal.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow();
        if (member != null) {
            likeService.cancelLike(itemId, String.valueOf(member.getId()));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/like/{itemId}")
    public ResponseEntity<String> addLike(@PathVariable Long itemId, Principal principal) {
        String email = principal.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow();

        log.info("itemId : " + itemId);
        log.info("email : " + email);
        log.info(String.valueOf(member));

        boolean result = false;

        if (member.getId() != null)
            result = likeService.addLike(itemId, String.valueOf(member.getId()));

        return result ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>("무슨 에러?",HttpStatus.BAD_REQUEST);
    }
}
