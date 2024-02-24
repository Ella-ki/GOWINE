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
    // Review
    private Long id;
    private String comment;
    private int rating;
    // Summary information
    private double averageGrade;
    private int reviewCount;

    // --------------------------------------------------------------------------------
    // ItemImg
    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private List<Long> reviewImgIds = new ArrayList<>();

    // --------------------------------------------------------------------------------

    // ModelMapper
    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview(){
        Review review = modelMapper.map(this, Review.class);

        return review;
    }

    public static ReviewFormDto of(Review review) {
        return modelMapper.map(review, ReviewFormDto.class);
    }

    // Setters for summary information
    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    private List<Review> reviews;

    public void addReview(Review review) {
        if (this.reviews == null) {
            this.reviews = new ArrayList<>();
        }
        this.reviews.add(review);
    }
}
