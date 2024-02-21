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
    private Long id;
    private String text;
    private Integer grade;
    private Long reviewCount;
}
