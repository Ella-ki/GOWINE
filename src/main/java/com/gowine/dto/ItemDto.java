package com.gowine.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String itemNm;
    private String winary;
    private String wineType;
    private String wineRegion;
    private String wineGrape;
    private Double vivinoRate;
    private Integer sweetnessPercent;
    private Integer acidityPercent;
    private Integer bodyPercent;
    private Integer tanninPercent;
    private Integer regularPrice;
    private Double avgRating; // 리뷰 평균
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private Long likeCount;
    private Long reviewCount;
}
