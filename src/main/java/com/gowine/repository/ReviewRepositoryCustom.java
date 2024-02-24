package com.gowine.repository;

import com.gowine.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<Review> getReviewPage(Pageable pageable);
}
