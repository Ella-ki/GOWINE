package com.gowine.repository;

import com.gowine.entity.Item;
import com.gowine.entity.ItemLike;
import com.gowine.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    Optional<Integer> countByItem(Item item);
    Optional<ItemLike> findByMemberAndItem(Member member, Item item);
}
