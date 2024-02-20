package com.gowine.service;

import com.gowine.repository.ItemRepository;
import com.gowine.repository.ItemLikeRepository;
import com.gowine.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class LikeServiceTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemLikeRepository likeRepository;
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