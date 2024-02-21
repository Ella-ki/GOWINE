package com.gowine.service;

import com.gowine.dto.*;
import com.gowine.entity.*;
import com.gowine.repository.ReviewImgRepository;
import com.gowine.repository.ReviewRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgService reviewImgService;
    private final ReviewImgRepository reviewImgRepository;

    public Long saveReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList,
                           Long itemId, String member) throws Exception {
        Review review = reviewFormDto.createReview();

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
}
