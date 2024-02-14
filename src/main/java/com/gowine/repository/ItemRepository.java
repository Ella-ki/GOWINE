package com.gowine.repository;

import com.gowine.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
    List<Item> findByItemNm(String itemNm);

    List<Item> findByPriceLessThan(Integer price);
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // select i from Item i  :: 테이블 Item 을 i로 치환해서 출력
    // i.itemDetail like     :: like 뒤에 선언된 값을 포함되면 출력
    // %:itemDetail% => @Param("itemDetail") String itemDetail :: String itemDetail like 를 할 값
    // order by i.price desc" :: Item 가격을 가지고 내림차순
/*
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
    
    // nativeQuery = true 이기 때문에 * 사용 할 수 있음
    @Query(value = "select * from Item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
*/
}
