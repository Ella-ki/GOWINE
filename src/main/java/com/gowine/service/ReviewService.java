package com.gowine.service;

import com.gowine.dto.*;
import com.gowine.entity.*;
import com.gowine.repository.ItemImgRepository;
import com.gowine.repository.ItemRepository;
import com.gowine.repository.ReviewImgRepository;
import com.gowine.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    private final ReviewRepository reviewRepository;
    private final ReviewImgService reviewImgService;
    private final ReviewImgRepository reviewImgRepository;

    public Long saveItem(ReviewDto reviewDto, List<MultipartFile> reviewImgFileList) throws Exception {
        // 상품 등록
        Review review = reviewDto.createReview(); // modelMapper => item <-> itemFormDto

        reviewRepository.save(review);

        // 이미지 등록
        for (int i = 0; i < reviewImgFileList.size(); i++) {
            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setReview(review);
            if(i == 0) {
                reviewImg.setRepImgYn("Y"); // 대표 이미지 설정
            } else {
                reviewImg.setRepImgYn("N");
            }
            reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
        }

        return review.getId();
    }

    @Transactional(readOnly = true)
    public ReviewDto getReviewDtl(Long reviewId){
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);

        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

        for (ReviewImg reviewImg : reviewImgList) {
            ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
            reviewImgDtoList.add(reviewImgDto);
        }

        Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
        ReviewDto reviewDto = ReviewDto.of(review);
        reviewDto.setReviewImgDtoList(reviewImgDtoList);

        return reviewDto;
    }
}
