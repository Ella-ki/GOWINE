package com.gowine.controller;

import com.gowine.dto.*;
import com.gowine.entity.Member;
import com.gowine.entity.Review;
import com.gowine.entity.ReviewImg;
import com.gowine.repository.MemberRepository;
import com.gowine.repository.ReviewImgRepository;
import com.gowine.repository.ReviewRepository;
import com.gowine.service.ItemService;
import com.gowine.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberRepository memberRepository;
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;

    @GetMapping(value = {"/product/list", "/product/list/{page}"})
    public String itemList(@RequestParam(name = "wineType", required = false) String wineType,
                           @RequestParam(name = "wineGrape", required = false) String wineGrape,
                           @RequestParam(name = "wineRegion", required = false) String wineRegion,
                           @RequestParam(name = "winePrice", required = false) Integer winePrice,
                           @RequestParam(name = "vivinoRate", required = false) Double vivinoRate,
                           @RequestParam(name = "rating", required = false) Integer rating,
                           @RequestParam(name = "itemSellStatus", required = false) String itemSellStatus,
                           Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 8);

        // wineType, wineGrape, wineRegion, winePrice, wineRate 등을 활용하여 필터링된 상품 리스트를 가져옴
        Page<MainItemDto> items = itemService.getFilteredItem(wineType, wineGrape, wineRegion, winePrice, vivinoRate, rating, itemSellStatus, pageable);
        model.addAttribute("items", items);
        model.addAttribute("maxPage", 8);

        return "item/list";
    }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId,
                          Principal principal, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        // 관련 상품 리스트
        List<MainItemDto> relatedItems = itemService.getRelatedItem(itemId, itemId);

        boolean hasReviewed = false;
        if (principal != null) { // 로그인 객체 있다
            String email = principal.getName();
            Member loginMember = memberRepository.findByEmail(email).orElse(null);

            if (loginMember != null) { // 로그인한 사용자가 있을 경우에만 실행
                Long memberId = loginMember.getId();
                hasReviewed = reviewService.hasReviewed(itemId, memberId);
            }

            model.addAttribute("loginMember", principal.getName());
        }

        Page<Review> reviewPage = reviewRepository.findByItem_IdOrderByRegTimeDesc(itemId, pageable);
        List<Review> reviews = reviewPage.getContent(); // 현재 페이지의 리뷰 목록 가져오기

        // 리뷰 평균 점수 계산
        double averageRating = reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);

        // 리뷰 개수 계산
        int reviewCount = (int) reviewPage.getTotalElements(); // 전체 리뷰 개수 가져오기

        // 리뷰 평균 점수와 개수를 이용하여 리뷰 평균 정보 생성
        ReviewFormDto reviewFormDto = new ReviewFormDto();
        reviewFormDto.setAverageRating(averageRating);
        reviewFormDto.setReviewCount(reviewCount);

        // 리뷰 목록을 설정
        reviewFormDto.setReviews(reviews);

        model.addAttribute("item", itemFormDto);
        model.addAttribute("relatedItems", relatedItems);
        model.addAttribute("reviewFormDto", reviewFormDto);
        model.addAttribute("hasReviewed", hasReviewed);

        return "item/itemDtl";
    }


    // 찜 상품
    @GetMapping(value = "/favorite")
    public String myFavoriteItem(Principal principal, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8);

        String email = principal.getName();
        Member loginMember = memberRepository.findByEmail(email).orElseThrow();

        if(Objects.nonNull(loginMember)) {
            Page<MainItemDto> items = itemService.getLikeItemPage(loginMember, pageable);
            model.addAttribute("items", items);
            model.addAttribute("maxPage", 8);
        }

        return "member/myFavorite";
    }
}
