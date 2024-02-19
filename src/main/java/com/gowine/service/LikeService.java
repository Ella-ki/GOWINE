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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public boolean addLike(Long itemId, String email) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        Member member = memberRepository.findByEmail(email).orElseThrow();
        Long memberId = member.getId();

        System.out.println("=====================");
        System.out.println("item : " + item);
        System.out.println("member: " + member);
        System.out.println("=====================");

        System.out.println(isNotAlreadyLike(item.getId(), String.valueOf(memberId)));

        if( isNotAlreadyLike(item.getId(), String.valueOf(memberId)) ) {
            likeRepository.save(new ItemLike(item, member));
            return true;
        }
        return false;
    }

    public void cancelLike(Long itemId, String email) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        Member member = memberRepository.findByEmail(email).orElseThrow();

        ItemLike itemLike = likeRepository.findByItemIdAndMemberId(item.getId(), member.getId()).orElseThrow();
        likeRepository.delete(itemLike);
    }

    /*
     *   1. 좋아요를 count할 대상 item을 가져온다.
     *   2. 가져온 item으로 like테이블에 쿼리한 결과를 List에 담는다.
     *   3. 현재 로그인한 사용자가
     *       이미 해당 상품에 좋아요를 눌렀는지 검사하고 결과를 List에 담아 반환한다.
     * */
    public List<String> count(Long itemId, String email) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        Member member = memberRepository.findByEmail(email).orElseThrow();

        Integer itemLikeCount = likeRepository.countByItemId(item.getId()).orElse(0);

        List<String> resultData =
                new ArrayList<>(Arrays.asList(String.valueOf(itemLikeCount)));

        if (Objects.nonNull(member.getId())) {
            resultData.add(String.valueOf(isNotAlreadyLike(item.getId(), String.valueOf(member.getId()))));
            return resultData;
        }

        return resultData;
    }

    private boolean isNotAlreadyLike(Long itemId, String email) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        Member member = memberRepository.findByEmail(email).orElseThrow();

        return likeRepository.findByItemIdAndMemberId(item.getId(), member.getId()).isEmpty();
    }
}
