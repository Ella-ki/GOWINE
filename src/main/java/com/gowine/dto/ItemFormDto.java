package com.gowine.dto;

import com.gowine.constant.ItemSellStatus;
import com.gowine.constant.WineGrape;
import com.gowine.constant.WineRegion;
import com.gowine.constant.WineType;
import com.gowine.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemFormDto { // 입력 받은 아이템 DTO
    // Item
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    private String winary;

    @NotNull(message = "종류를 선택해주세요.")
    private WineType wineType;

    @NotNull(message = "품종을 선택해주세요.")
    private WineGrape wineGrape;

    @NotNull(message = "지역을 선택해주세요.")
    private WineRegion wineRegion;

    @NotNull(message = "당도를 선택해주세요.")
    private Integer sweetnessPercent;

    @NotNull(message = "산도를 선택해주세요.")
    private Integer acidityPercent;

    @NotNull(message = "바디감을 선택해주세요.")
    private Integer bodyPercent;

    @NotNull(message = "탄닌을 선택해주세요.")
    private Integer tanninPercent;

    private Double vivinoRate;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

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

