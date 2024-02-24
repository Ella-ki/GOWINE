package com.gowine.repository;

import com.gowine.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review> {
    Page<Review> findByItem_IdOrderByRegTimeDesc(Long itemId, Pageable pageable);
    Optional<Review> findByItemIdAndMemberId(Long itemId, Long memberId);

    Optional<Review> findByMemberAndItem(Member member, Item item);

    Page<Review> findAll(Pageable pageable);
}
