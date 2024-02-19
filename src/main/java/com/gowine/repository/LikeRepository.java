package com.gowine.repository;

import com.gowine.entity.Item;
import com.gowine.entity.ItemLike;
import com.gowine.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<ItemLike, Long> {
    Optional<Integer> countByItemId(Long itemId);
    Optional<ItemLike> findByItemIdAndMemberId(Long itemId, Long memberId);
}
