package com.gowine.controller;

import com.gowine.entity.Member;
import com.gowine.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping(value = "/like/{itemId}")
    public ResponseEntity<String> addLike(
            @AuthenticationPrincipal Member member,
            @PathVariable Long itemId) {

        boolean result = false;

        if (member != null) {
            result = likeService.addLike(itemId, member);
        }

        return result ?
                new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
