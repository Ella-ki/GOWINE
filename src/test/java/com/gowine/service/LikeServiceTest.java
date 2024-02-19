package com.gowine.service;

import com.gowine.entity.Item;
import com.gowine.entity.ItemLike;
import com.gowine.entity.Member;
import com.gowine.repository.ItemRepository;
import com.gowine.repository.LikeRepository;
import com.gowine.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class LikeServiceTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    MemberRepository memberRepository;

    /*
    @Test
    @DisplayName("상품 좋아요 테스트")
    public boolean addLike() {
        Item item = new Item();
        ItemLike itemLike = new ItemLike();

        item.setId(item.getId());

    }
    */

}