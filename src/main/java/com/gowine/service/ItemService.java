package com.gowine.service;

import com.gowine.constant.ItemSellStatus;
import com.gowine.dto.*;
import com.gowine.entity.Item;
import com.gowine.entity.ItemImg;
import com.gowine.entity.Member;
import com.gowine.repository.ItemImgRepository;
import com.gowine.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품 등록
        Item item = itemFormDto.createItem(); // modelMapper => item <-> itemFormDto

        itemRepository.save(item);

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            itemImgService.saveItemImg(itemImg,itemImgFileList.get(i));
        }

        // return 없이 void 로 해도되지만, 추후 어디에서 필요할지모르니 return 만듬
        // return 해놓고 안써도되니까..
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        // DB 에서 데이터를 가지고옴
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getFilteredItem(String wineType, String wineGrape, String wineRegion,
                                             Integer winePrice, Double vivinoRate, Integer rating, String itemSellStatus, Pageable pageable) {
        return itemRepository.getFilteredItemPage(wineType, wineGrape, wineRegion, winePrice, vivinoRate, itemSellStatus, pageable);
    }

    @Transactional(readOnly = true)
    public List<MainItemDto> getRelatedItem(Long itemId, Long excludedItemId){
        return itemRepository.getRelatedItemPage(itemId, excludedItemId);
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품 변경
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        // 상품 이미지 변경
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    public Long updateStatus(Long itemId) throws Exception {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        item.updateStatus();
        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto , pageable);
    }

    public void deleteItem(Long itemId) throws Exception {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        itemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getListItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getListItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public List<MainItemDto> getSearchItem(String keyword){
        return itemRepository.getSearchItemPage(keyword);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(Pageable pageable){
        return itemRepository.getMainItemPage(pageable);
    }

    @Transactional(readOnly = true)
    public List<MainItemDto> getMbtiItem(String mbti) {
        return itemRepository.getMbtiItemPage(mbti);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getLikeItemPage(Member loginMember, Pageable pageable) {
        return itemRepository.getLikeItemPage(loginMember, pageable);
    }

    @Transactional(readOnly = true)
    public ItemSellStatus getItemSellStatus(Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null) {
            return item.getItemSellStatus();
        }
        return null; // 해당하는 아이템이 없는 경우
    }

}






















