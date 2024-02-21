package com.gowine.repository;

import com.gowine.entity.Item;
import com.gowine.entity.ItemLike;
import com.gowine.entity.Member;
import com.gowine.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByItemId(Long itemId);

    Optional<Review> findByItem(Item item);

    Optional<Review> findByMemberAndItem(Member member, Item item);
}
