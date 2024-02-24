package com.gowine.repository;

import com.gowine.entity.QReview;
import com.gowine.entity.Review;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Review> getReviewPage(Pageable pageable){
        QueryResults<Review> results = queryFactory.selectFrom(QReview.review)
                .orderBy(QReview.review.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Review> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable,total);
    }

}
