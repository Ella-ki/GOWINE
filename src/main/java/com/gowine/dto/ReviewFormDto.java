package com.gowine.dto;

import com.gowine.entity.Item;
import com.gowine.entity.Member;
import com.gowine.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewFormDto {
    private Long itemId; // 아이템 ID
    private Long memberId; // 멤버 ID
    private String text;
    private int grade;

    // --------------------------------------------------------------------------------
    // ItemImg
    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private List<Long> reviewImgIds = new ArrayList<>();

    // --------------------------------------------------------------------------------

    // ModelMapper
    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview(Member member, Item item){
        Review review = modelMapper.map(this, Review.class);
        review.setMember(member);
        review.setItem(item);
        return review;
    }

    public static ReviewFormDto of(Review review) {
        return modelMapper.map(review, ReviewFormDto.class);
    }
}
