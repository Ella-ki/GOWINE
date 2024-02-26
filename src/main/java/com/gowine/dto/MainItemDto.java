package com.gowine.dto;

import com.gowine.constant.ItemSellStatus;
import com.gowine.constant.WineGrape;
import com.gowine.constant.WineRegion;
import com.gowine.constant.WineType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class MainItemDto {
    private Long id;
    private String itemNm;
    private String winary;
    private WineType wineType;
    private WineRegion wineRegion;
    private WineGrape wineGrape;
    private Double vivinoRate;
    private String imgUrl;
    private Integer price;
    private ItemSellStatus itemSellStatus;

    @QueryProjection // Querydsl 결과 조회 시 MainItemDto 객체로 바로 오도록 활용, entity 없이 Q만 만들도록
    public MainItemDto(Long id, String itemNm, String winary, WineType wineType,
                       WineRegion wineRegion, WineGrape wineGrape, Double vivinoRate,
                       String imgUrl, Integer price, ItemSellStatus itemSellStatus) {
        this.id = id;
        this.itemNm = itemNm;
        this.winary = winary;
        this.wineType = wineType;
        this.wineRegion = wineRegion;
        this.wineGrape = wineGrape;
        this.vivinoRate = vivinoRate;
        this.imgUrl = imgUrl;
        this.price = price;
        this.itemSellStatus = itemSellStatus;
    }
}
