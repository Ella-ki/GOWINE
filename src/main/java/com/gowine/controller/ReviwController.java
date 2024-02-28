package com.gowine.controller;

import com.gowine.dto.ReviewDto;
import com.gowine.dto.ReviewFormDto;
import com.gowine.entity.Item;
import com.gowine.entity.Member;
import com.gowine.entity.Review;
import com.gowine.repository.MemberRepository;
import com.gowine.service.MemberService;
import com.gowine.service.OrderService;
import com.gowine.service.ReviewImgService;
import com.gowine.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReviwController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewImgService reviewImgService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/board/notice")
    public String noticeList(){
        return "board/noticeList";
    }

    @GetMapping(value = {"/board/community", "/board/community/{page}"})
    public String boardList(Model model, @PathVariable("page") Optional<Integer> page, Principal principal){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        Page<ReviewDto> reviewDtos = reviewService.getAllReviews(pageable);

        /*
        List<Boolean> hasPurchasedList = new ArrayList<>();
        if (principal != null) {
            String email = principal.getName();
            Member loginMember = memberRepository.findByEmail(email).orElseThrow();

            for (ReviewDto reviewDto : reviewDtos.getContent()) {
                boolean hasPurchased = reviewService.hasPurchasedItem(loginMember, reviewDto.getItemId());
                hasPurchasedList.add(hasPurchased);
            }
        }
        */

        model.addAttribute("reviewItem", reviewDtos);
        model.addAttribute("maxPage", 5);
        //model.addAttribute("hasPurchasedList", hasPurchasedList);
        return "review/communityList";
    }

    @GetMapping(value = "/review/new")
    public String reviewForm(Model model) {
        model.addAttribute("reviewFormDto", new ReviewFormDto());
        return "review/register";
    }

    @PostMapping(value = "/review/new")
    ResponseEntity addReview(@Valid ReviewFormDto reviewFormDto, Model model,
                           @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList,
                           @RequestParam("itemId") Long itemId, Principal principal) {

        String email = principal.getName();
        Member loginMember = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("로그인한 멤버를 찾을 수 없습니다."));
        Long reviewItemId;

        try {
            reviewItemId = reviewService.saveReview(reviewFormDto, reviewImgFileList, itemId, loginMember );
        } catch (Exception e){
            model.addAttribute("errorMsg", "리뷰 등록 중 에러가 발생하였습니다.");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(reviewItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/review/{reviewId}")
    public String reviewDtl(@PathVariable("reviewId") Long reviewId, Model model) {
        try {
            ReviewFormDto reviewFormDto = reviewService.getReviewDtl(reviewId);
            model.addAttribute("reviewFormDto", reviewFormDto);

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMsg", "존재하지않는 리뷰입니다.");
            model.addAttribute("reviewFormDto", new ReviewFormDto());
            return "item/list";
        }

        return "review/register";
    }


    @PostMapping(value = "/review/{reviewId}")
    public String reviewUpdate(@Valid ReviewFormDto reviewFormDto,
                             @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList, Model model) {

        try{
            reviewService.updateReview(reviewFormDto, reviewImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMsg", "리뷰 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/";
    }
}
