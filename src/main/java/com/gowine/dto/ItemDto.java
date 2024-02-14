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
    private Integer discountPrice; // 타임세일에 사용
    private Double avgRating; // 평점 평균
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
