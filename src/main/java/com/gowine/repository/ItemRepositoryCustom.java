package com.gowine.repository;

import com.gowine.dto.ItemFormDto;
import com.gowine.dto.ItemSearchDto;
import com.gowine.dto.MainItemDto;
import com.gowine.entity.Item;
import com.gowine.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getListItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    List<MainItemDto> getSearchItemPage(String keyword);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    List<MainItemDto> getMbtiItemPage(String mbti);

    Page<MainItemDto> getLikeItemPage(Member loginMember, Pageable pageable);

    List<MainItemDto> getRelatedItemPage(Long itemid, Long excludedItemId);

    Page<MainItemDto> getFilteredItemPage(String wineType, String wineGrape, String wineRegion,
                                          Integer winePrice, Double vivinoRate, Pageable pageable);

}
