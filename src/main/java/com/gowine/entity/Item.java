package com.gowine.entity;

import com.gowine.constant.ItemSellStatus;
import com.gowine.constant.WineGrape;
import com.gowine.constant.WineRegion;
import com.gowine.constant.WineType;
import com.gowine.dto.ItemFormDto;
import com.gowine.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity{
    @Id // 기본키
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "wine_name", nullable = false, length = 50)
    private String itemNm;

    private String winary;

    @Enumerated(EnumType.STRING)
    private WineType wineType;

    @Enumerated(EnumType.STRING)
    private WineGrape wineGrape;

    @Enumerated(EnumType.STRING)
    private WineRegion wineRegion;

    @Column(name="style_sweetness", nullable = false)
    private int sweetnessPercent;

    @Column(name="style_acidity", nullable = false)
    private int acidityPercent;

    @Column(name="style_body", nullable = false)
    private int bodyPercent;

    @Column(name="style_tannin", nullable = false)
    private int tanninPercent;

    private Double vivinoRate;

    @Column(name="regular_price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

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

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    Set<ItemLike> likes = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    Set<Review> reviews = new HashSet<>();

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.winary = itemFormDto.getWinary();
        this.wineType = itemFormDto.getWineType();
        this.wineGrape = itemFormDto.getWineGrape();
        this.wineRegion = itemFormDto.getWineRegion();
        this.acidityPercent = itemFormDto.getAcidityPercent();
        this.bodyPercent = itemFormDto.getBodyPercent();
        this.sweetnessPercent = itemFormDto.getSweetnessPercent();
        this.tanninPercent = itemFormDto.getTanninPercent();
        this.vivinoRate = itemFormDto.getVivinoRate();
        this.stockNumber = itemFormDto.getStockNumber();
    }

    public void updateStatus() {
        this.itemSellStatus = ItemSellStatus.SOLD_OUT;
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
















