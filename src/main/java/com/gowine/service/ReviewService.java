package com.gowine.service;

import com.gowine.dto.*;
import com.gowine.entity.*;
import com.gowine.repository.ItemRepository;
import com.gowine.repository.ReviewImgRepository;
import com.gowine.repository.ReviewRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgService reviewImgService;
    private final ReviewImgRepository reviewImgRepository;
    private final ItemRepository itemRepository;

    public Long saveReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList,
                           Long itemId, Member member) throws Exception {

        Item item = itemRepository.findById(itemId).orElseThrow();

        System.out.println("서비스 아이템 : " + item);
        System.out.println("멤버 : " + member);
        System.out.println("reviewFormDto : " + reviewFormDto);

        Review review = reviewFormDto.createReview();

        if( isNotAlreadyReview(member, item) ) {
            reviewRepository.save(review);
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

            return review.getId();
        }

        return review.getId();
    }

    private boolean isNotAlreadyReview(Member member, Item item) {
        return reviewRepository.findByMemberAndItem(member, item).isEmpty();
    }
}
