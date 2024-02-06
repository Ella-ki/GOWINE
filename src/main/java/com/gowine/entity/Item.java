package com.gowine.entity;

import com.gowine.constant.ItemSellStatus;
import com.gowine.dto.ItemFormDto;
import com.gowine.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
    @Id // 기본키
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "wine_name", nullable = false, length = 50)
    private String itemNm;

    // 와인 타입 - 레드, 화이트 ...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private WineType type;

    // 와인 품종 - 말백, 쇼블 ...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grape_id")
    private WineGrape grape;

    // 와인 지역 - Maipo Valley, Proto ...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private WineRegion region;

    // 와이너리 - Domaine, Lafage ...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winery_id")
    private Winery winery;

    // 당도, 바디감, 탄닌, 산도
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id")
    private WineStyle wineStyle;

    // 비비노 평점
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_id")
    private VivinoRating vivinoRating;

    @Column(name="regular_price", nullable = false)
    private int regularPrice;

    @Column(nullable = false)
    private int stockNumber;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Review> reviews; // 상품 리뷰 관계 추가

    // 상품 평균 평점
    @Column(name = "avg_rating")
    private Double avgRating;
    
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    @ManyToMany(fetch = FetchType.LAZY) // 어떤 member_id 에게 팔렸는지 정의
    @JoinTable(
            name = "member_item",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Member> member;

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        //this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        //this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고 수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock; // 변경 감지
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}
















