package com.gowine.controller;

import com.gowine.dto.ItemFormDto;
import com.gowine.dto.ReviewDto;
import com.gowine.dto.ReviewFormDto;
import com.gowine.entity.Member;
import com.gowine.repository.MemberRepository;
import com.gowine.service.ReviewImgService;
import com.gowine.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviwController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewImgService reviewImgService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping(value = "/board/community")
    public String boardList(){
        return "board/communityList";
    }

    @GetMapping(value = "/board/notice")
    public String noticeList(){
        return "board/noticeList";
    }

    @GetMapping(value = "/review/new")
    public String reviewForm(Model model) {
        System.out.println("ReviewFormDto : " + new ReviewFormDto());

        model.addAttribute("reviewFormDto", new ReviewFormDto());

        return "review/register";
    }

    @PostMapping(value = "/review/new")
    ResponseEntity addReview(@Valid ReviewFormDto reviewFormDto, Model model,
                           @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList,
                           @RequestParam("itemId") Long itemId, Principal principal) {

        String email = principal.getName();
        Member loginMember = memberRepository.findByEmail(email).orElseThrow();
        Long reviewItemId;

        try {
            System.out.println("reviewFormDto before: " + reviewFormDto);
            reviewItemId = reviewService.saveReview(reviewFormDto, reviewImgFileList, itemId, loginMember.getId());
            System.out.println("review save");
            System.out.println("reviewFormDto : " + reviewFormDto);

        } catch (Exception e){
            model.addAttribute("errorMsg", "리뷰 등록 중 에러가 발생하였습니다.");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(reviewItemId, HttpStatus.OK);
    }
}
