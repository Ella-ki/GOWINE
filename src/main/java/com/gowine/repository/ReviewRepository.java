package com.gowine.repository;

import com.gowine.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review>, ReviewRepositoryCustom  {
    Review findByMemberId(Long memberId);

    Page<Review> findByItem_Id(Long itemId, Pageable pageable);
    Optional<Review> findByItemIdAndMemberId(Long itemId, Long memberId);


    Optional<Integer> countByItem(Item item);
    boolean existsByItemAndMember(Item item, Member member);

    boolean existsByItem_IdAndMember_Id(Long itemId, Long memberId);

    Optional<Review> findByMemberAndItem(Member member, Item item);
}
