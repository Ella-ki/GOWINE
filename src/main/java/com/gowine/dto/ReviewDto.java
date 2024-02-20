package com.gowine.dto;


import com.gowine.entity.Item;
import com.gowine.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ReviewDto { // 입력 받은 리뷰 DTO
     private Long id;

     @NotBlank
     private String reviewText;

     @NotNull
     private Double reviewRate;

    // --------------------------------------------------------------------------------
    // ItemImg
    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private List<Long> reviewImgIds = new ArrayList<>();

    // --------------------------------------------------------------------------------

    // ModelMapper
    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview(){
        // ReviewDto -> Review 연결
        return modelMapper.map(this, Review.class);
    }

    public static ReviewDto of(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }
}
