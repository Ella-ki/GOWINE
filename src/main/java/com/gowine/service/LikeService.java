package com.gowine.service;

import com.gowine.entity.Item;
import com.gowine.entity.ItemLike;
import com.gowine.entity.Member;
import com.gowine.repository.ItemRepository;
import com.gowine.repository.LikeRepository;
import com.gowine.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

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

    /*
     *   1. 좋아요를 count할 대상 item을 가져온다.
     *   2. 가져온 item으로 like테이블에 쿼리한 결과를 List에 담는다.
     *   3. 현재 로그인한 사용자가
     *       이미 해당 상품에 좋아요를 눌렀는지 검사하고 결과를 List에 담아 반환한다.
     * */
    public List<String> count(Long itemId, Member loginMember) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        Integer itemLikeCount = likeRepository.countByItem(item).orElse(0);

        List<String> resultData =
                new ArrayList<>(Arrays.asList(String.valueOf(itemLikeCount)));

        if (Objects.nonNull(loginMember)) {
            resultData.add(String.valueOf(isNotAlreadyLike(loginMember, item)));
            return resultData;
        }

        return resultData;
    }


    private boolean isNotAlreadyLike(Member member, Item item) {
        return likeRepository.findByMemberAndItem(member, item).isEmpty();
    }
}
