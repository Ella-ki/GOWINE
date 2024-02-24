package com.gowine.dto;

import com.gowine.constant.WineGrape;
import com.gowine.constant.WineRegion;
import com.gowine.constant.WineType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private Long itemId;
    private Long memberId;
    private String memberName;
    private String comment;
    private Integer rating;
    //private Long reviewCount;
    private String itemNm;
    private String winary;
    private WineType wineType;
    private WineRegion wineRegion;
    private WineGrape wineGrape;
    private Double vivinoRate;
    //private String reviewImgUrl;
    //private String itemImgUrl;
    private LocalDateTime regTime;

    // 아이템 이미지 URL 리스트
    private List<String> itemImgUrlList;

    // 리뷰 이미지 URL 리스트
    private List<String> reviewImgUrlList;

    public ReviewDto(Long reviewId, Long itemId, Long memberId, String memberName, String comment, Integer rating, String itemNm,
                     String winary, WineType wineType, WineRegion wineRegion, WineGrape wineGrape, Double vivinoRate, LocalDateTime regTime) {
        this.reviewId = reviewId;
        this.itemId = itemId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.comment = comment;
        this.rating = rating;
        this.itemNm = itemNm;
        this.winary = winary;
        this.wineType = wineType;
        this.wineRegion = wineRegion;
        this.wineGrape = wineGrape;
        this.vivinoRate = vivinoRate;
        this.regTime = regTime;
    }
}
