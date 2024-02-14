package com.gowine.repository;

import com.gowine.dto.ItemFormDto;
import com.gowine.dto.ItemSearchDto;
import com.gowine.dto.MainItemDto;
import com.gowine.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getListItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    List<MainItemDto> getSearchItemPage(String keyword);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    List<MainItemDto> getMbtiItemPage(String mbti);
}
