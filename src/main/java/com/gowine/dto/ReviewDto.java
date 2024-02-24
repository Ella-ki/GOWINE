package com.gowine.dto;

import com.gowine.entity.Item;
import com.gowine.entity.Review;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewDto {
    private Long reviewId;
    private String comment;
    private Integer rating;
    private Long reviewCount;
    private String imgUrl;
    private String itemNm;
    private String winary;
    private String wineType;
    private String wineRegion;
    private String wineGrape;
    private Double vivinoRate;

    public ReviewDto(Long reviewId, String comment, Integer rating, Long reviewCount, String imgUrl, String itemNm,
                     String winary, String wineType, String wineRegion, String wineGrape, Double vivinoRate) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.imgUrl = imgUrl;
        this.itemNm = itemNm;
        this.winary = winary;
        this.wineType = wineType;
        this.wineRegion = wineRegion;
        this.wineGrape = wineGrape;
        this.vivinoRate = vivinoRate;
    }
}
