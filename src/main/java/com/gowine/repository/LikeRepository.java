package com.gowine.repository;

import com.gowine.entity.Item;
import com.gowine.entity.ItemLike;
import com.gowine.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<ItemLike, Long> {
    Optional<ItemLike> findByItemAndMember(Item item, Member member);
}
