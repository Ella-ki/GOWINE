package com.gowine.service;

import com.gowine.entity.Item;
import com.gowine.entity.ItemLike;
import com.gowine.entity.Member;
import com.gowine.repository.ItemRepository;
import com.gowine.repository.ItemLikeRepository;
import com.gowine.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class ItemLikeService {
    private final ItemLikeRepository likeRepository;
    private final ItemRepository itemRepository;

    public boolean addLike(Member member, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        if( isNotAlreadyLike(member, item) ) {
            likeRepository.save(new ItemLike(item, member));
            return true;
        }
        return false;
    }

    public void cancelLike(Member member, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        ItemLike itemLike = likeRepository.findByMemberAndItem(member, item).orElseThrow();
        likeRepository.delete(itemLike);
    }

    public List<String> count(Long itemId, Member loginMember) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        Integer itemLikeCount = likeRepository.countByItem(item).orElse(0);

        // 아이템 id 로 가져온 아이템을 like 테이블에 쿼리한 결과를 List 에 담기
        List<String> resultData =
                new ArrayList<>(Arrays.asList(String.valueOf(itemLikeCount)));

        if (Objects.nonNull(loginMember)) { // 현재 로그인한 사용자가 해당 상품에 좋아요 눌렀는지 조건
            resultData.add(String.valueOf(isNotAlreadyLike(loginMember, item)));
            return resultData;
        }

        return resultData;
    }

    private boolean isNotAlreadyLike(Member member, Item item) {
        return likeRepository.findByMemberAndItem(member, item).isEmpty();
    }
}
