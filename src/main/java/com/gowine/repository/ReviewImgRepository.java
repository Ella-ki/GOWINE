package com.gowine.repository;

import com.gowine.entity.ItemImg;
import com.gowine.entity.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {
    List<ReviewImg> findByReviewIdOrderByIdAsc(Long reviewId);
    ReviewImg findByReviewIdAndRepImgYn(Long reviewId, String repImgYn);
}
