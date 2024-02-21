package com.gowine.dto;

import com.gowine.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewFormDto {
    private Long id;

    @NotBlank(message = "리뷰 내용은 필수 입력 값입니다.")
    private String text;

    @NotNull(message = "별점은 필수 입력 값입니다.")
    private int grade;

    // --------------------------------------------------------------------------------
    // ItemImg
    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private List<Long> reviewImgIds = new ArrayList<>();

    // --------------------------------------------------------------------------------

    // ModelMapper
    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview(){
        // ReviewFormDto -> Review 연결
        return modelMapper.map(this, Review.class);
    }

    public static ReviewFormDto of(Review review) {
        return modelMapper.map(review, ReviewFormDto.class);
    }
}
