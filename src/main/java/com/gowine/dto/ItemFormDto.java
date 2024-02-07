package com.gowine.dto;

import com.gowine.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto { // 입력 받은 아이템 DTO
    // Item
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    private String winery;

    @NotNull(message = "와인의 종류를 선택해주세요.")
    private String wineType;

    @NotNull(message = "와인의 품종을 선택해주세요.")
    private String wineGrape;

    @NotNull(message = "와인의 지역을 선택해주세요.")
    private String wineRegion;

    @NotNull(message = "와인의 스타일을 선택해주세요.")
    private int sweetnessPercent;

    @NotNull(message = "와인의 스타일을 선택해주세요.")
    private int acidityPercent;

    @NotNull(message = "와인의 스타일을 선택해주세요.")
    private int bodyPercent;

    @NotNull(message = "와인의 스타일을 선택해주세요.")
    private int tanninPercent;

    private String vivinoRate;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    // --------------------------------------------------------------------------------
    // ItemImg
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    // --------------------------------------------------------------------------------
    // ItemImg
    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private List<Long> reviewImgIds = new ArrayList<>();

    // --------------------------------------------------------------------------------

    // ModelMapper
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        // ItemFormDto -> Item 연결
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }

}

