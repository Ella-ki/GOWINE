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

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean addLike(Long itemId, Member member) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        //중복 좋아요 방지
        if(isNotAlreadyLike(item, member)) {
            likeRepository.save(new ItemLike(item, member));
            return true;
        }

        return false;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(Item item, Member member) {
        return likeRepository.findByItemAndMember(item, member).isEmpty();
    }
}
