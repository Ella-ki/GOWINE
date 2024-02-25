package com.gowine.service;

import com.gowine.dto.*;
import com.gowine.entity.*;
import com.gowine.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgService reviewImgService;
    private final ReviewImgRepository reviewImgRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long saveReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList, Long itemId, Member loginMember) throws Exception {
        // 해당 상품에 대한 리뷰를 조회
        Review existingReview = reviewRepository.findByMemberAndItem(loginMember, itemRepository.findById(itemId).orElse(null)).orElse(null);

        // 리뷰가 이미 존재하는 경우에는 수정하고, 그렇지 않은 경우에는 새로운 리뷰를 저장합니다.
        if(existingReview != null) {
            // 기존 리뷰를 업데이트합니다.
            existingReview.updateReview(reviewFormDto);

            // 리뷰 이미지 업데이트 등 기타 필요한 업데이트 작업을 수행할 수 있습니다.
            for (int i = 0; i < reviewImgFileList.size(); i++) {
                ReviewImg reviewImg = new ReviewImg();
                reviewImg.setReview(existingReview);
                if(i == 0) {
                    reviewImg.setRepImgYn("Y");
                } else {
                    reviewImg.setRepImgYn("N");
                }
                reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
            }

            // 리뷰 저장
            reviewRepository.save(existingReview);

            return existingReview.getId(); // 수정된 리뷰의 ID를 반환합니다.
        } else {
            // 새로운 리뷰를 저장
            Review review = reviewFormDto.createReview();
            review.setMember(loginMember);
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다."));
            review.setItem(item);
            reviewRepository.save(review);

            // 리뷰 이미지 저장
            for (int i = 0; i < reviewImgFileList.size(); i++) {
                ReviewImg reviewImg = new ReviewImg();
                reviewImg.setReview(review);
                if(i == 0) {
                    reviewImg.setRepImgYn("Y");
                } else {
                    reviewImg.setRepImgYn("N");
                }
                reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
            }

            return review.getId(); // 새로운 리뷰의 ID를 반환합니다.
        }
    }

    public Page<ReviewDto> getAllReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return reviews.map(review -> {
            Item item = review.getItem();
            Member member = review.getMember();

            Long itemId = review.getItem().getId();

            List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

            // 아이템 이미지 정보를 ItemImgDto 리스트로 변환
            List<ItemImgDto> itemImgDtoList = itemImgList.stream()
                    .map(ItemImgDto::of)
                    .collect(Collectors.toList());

            ItemFormDto itemFormDto = ItemFormDto.of(item);
            itemFormDto.setItemImgDtoList(itemImgDtoList);

            Long reviewId = review.getId();

            List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);

            List<ReviewImgDto> reviewImgDtoList = reviewImgList.stream()
                    .map(ReviewImgDto::of)
                    .collect(Collectors.toList());

            ReviewFormDto reviewFormDto = ReviewFormDto.of(review);
            reviewFormDto.setReviewImgDtoList(reviewImgDtoList);

            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewId(review.getId());
            reviewDto.setRating(review.getRating());
            reviewDto.setComment(review.getComment());
            reviewDto.setItemNm(review.getItem().getItemNm());
            reviewDto.setMemberName(review.getMember().getName());
            reviewDto.setWinary(item.getWinary());
            reviewDto.setWineType(item.getWineType());
            reviewDto.setWineRegion(item.getWineRegion());
            reviewDto.setWineGrape(item.getWineGrape());
            reviewDto.setWineType(item.getWineType());
            reviewDto.setRegTime(review.getRegTime());
            reviewDto.setVivinoRate(review.getItem().getVivinoRate());

            reviewDto.setItemImgUrlList(itemImgDtoList.stream()
                    .map(ItemImgDto::getImgUrl)
                    .collect(Collectors.toList()));

            reviewDto.setReviewImgUrlList(reviewImgDtoList.stream()
                    .map(ReviewImgDto::getImgUrl)
                    .collect(Collectors.toList()));

            return reviewDto;
        });
    }

    public ReviewFormDto getItemReviews(Long itemId, Pageable pageable){
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

        if (!reviews.isEmpty()) {
            Long reviewId = reviews.get(0).getId();

            List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);

            List<ReviewImgDto> reviewImgDtoList = reviewImgList.stream()
                    .map(ReviewImgDto::of)
                    .collect(Collectors.toList());

            reviewFormDto.setReviewImgDtoList(reviewImgDtoList);
        }
        return reviewFormDto;
    }

    @Transactional(readOnly = true)
    public ReviewFormDto getReviewDtl(Long reviewId){
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);

        // DB에서 리뷰 정보를 가져옴
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);

        // ReviewImg가 없을 경우 빈 리스트를 생성
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

        for (ReviewImg reviewImg : reviewImgList) {
            ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
            reviewImgDtoList.add(reviewImgDto);
        }

        ReviewFormDto reviewFormDto = ReviewFormDto.of(review);
        reviewFormDto.setReviewImgDtoList(reviewImgDtoList);
        return reviewFormDto;
    }

    public Long updateReview(ReviewFormDto reivewFormDto, List<MultipartFile> reviewImgFileList) throws Exception {
        // 리뷰 변경
        Review review = reviewRepository.findById(reivewFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        review.updateReview(reivewFormDto);

        List<Long> reviewImgIds = reivewFormDto.getReviewImgIds();

        for (int i = 0; i < reviewImgFileList.size(); i++) {
            reviewImgService.updateReviewImg(reviewImgIds.get(i), reviewImgFileList.get(i));
        }
        return review.getId();
    }

    public void deleteReview(Long reviewId) throws Exception {
        Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
        reviewRepository.delete(review);
    }

    public boolean hasReviewed(Long itemId, Long memberId) {
        Optional<Review> review = reviewRepository.findByItemIdAndMemberId(itemId, memberId);
        return review.isPresent();
    }

    public boolean hasPurchasedItem(Member member, Long itemId) {
        return orderRepository.existsByMemberIdAndOrderItems_Item_Id(member, itemId);
    }
}
