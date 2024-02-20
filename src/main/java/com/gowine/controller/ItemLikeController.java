package com.gowine.controller;

import com.gowine.entity.Member;
import com.gowine.repository.MemberRepository;
import com.gowine.service.ItemLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemLikeController {
    private final ItemLikeService likeService;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/like/{itemId}")
    public ResponseEntity<List<String>> getLikeCount(@PathVariable Long itemId, Principal principal) {
        String email = principal.getName();
        Member loginMember = memberRepository.findByEmail(email).orElse(null);
        List<String> resultData = likeService.count(itemId, loginMember);

        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @DeleteMapping(value = "/like/{itemId}")
    public ResponseEntity<String> cancelLike(@PathVariable Long itemId, Principal principal) {
        String email = principal.getName();
        Member loginMember = memberRepository.findByEmail(email).orElseThrow();
        if (loginMember != null) {
            likeService.cancelLike(loginMember, itemId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/like/{itemId}")
    public ResponseEntity<String> addLike(Principal principal,
                                          @PathVariable Long itemId) {
        boolean result = false;

        String email = principal.getName();
        Member loginMember = memberRepository.findByEmail(email).orElseThrow();

        if (Objects.nonNull(loginMember))
            result = likeService.addLike(loginMember, itemId);

        return result ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
