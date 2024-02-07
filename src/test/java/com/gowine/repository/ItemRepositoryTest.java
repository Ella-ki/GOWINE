package com.gowine.repository;

import com.gowine.entity.Item;
import com.gowine.entity.WineType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링부트 테스트를 의미
@TestPropertySource(locations = "classpath:application-test.properties") // 테스트에 사용하는 설정 소스
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager entityManager;

    @Test // 테스트 메소드
    @DisplayName("상품 저장 테스트") // 테스트 이름 설정 -> 전체 테스트일 때 에러 발생 부분이 어딘지 정확히 알기 위함
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setAvgRating(4.5);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        // itemRepository -> save insert 실제 테이블
        // 테스트는 H2 DB -> 경량화 되어있어서 빠르기 때문에 테스트에 활용

        // 더미 데이터 넣은것을 그대로 리턴, mysql은 int 리턴하잖아?
        // int 리턴 -> insert 하면 1행이 추가됐다는 의미로..1을 리턴
        Item savedItem = itemRepository.save(item);

        // Object 클래스(최상위 부모) 상속 받아서 오버라이딩
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for (int i = 1; i <=10 ; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }


}