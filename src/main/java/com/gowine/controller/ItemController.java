package com.gowine.controller;

import com.gowine.dto.ItemFormDto;
import com.gowine.dto.ItemSearchDto;
import com.gowine.dto.MainItemDto;
import com.gowine.entity.Member;
import com.gowine.repository.MemberRepository;
import com.gowine.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/product/list")
    public String itemList(@RequestParam(name = "wineType", required = false) String wineType,
                           @RequestParam(name = "wineGrape", required = false) String wineGrape,
                           @RequestParam(name = "wineRegion", required = false) String wineRegion,
                           @RequestParam(name = "winePrice", required = false) Integer winePrice,
                           @RequestParam(name = "vivinoRate", required = false) Double vivinoRate,
                           Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 8);

        // wineType, wineGrape, wineRegion, winePrice, wineRate 등을 활용하여 필터링된 상품 리스트를 가져옴
        Page<MainItemDto> items = itemService.getFilteredItem(wineType, wineGrape, wineRegion, winePrice, vivinoRate, pageable);
        model.addAttribute("items", items);
        model.addAttribute("maxPage", 8);

        return "item/list";
    }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        // 관련 상품 리스트
        List<MainItemDto> relatedItems = itemService.getRelatedItem(itemId, itemId);

        model.addAttribute("item", itemFormDto);
        model.addAttribute("relatedItems", relatedItems);

        return "item/itemDtl";
    }

    // 찜 상품
    @GetMapping(value = "/favorite")
    public String myFavoriteItem(Principal principal, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8);

        String email = principal.getName();
        Member loginMember = memberRepository.findByEmail(email).orElseThrow();

        if(Objects.nonNull(loginMember)) {
            Page<MainItemDto> items = itemService.getLikeItemPage(loginMember, pageable);
            model.addAttribute("items", items);
            model.addAttribute("maxPage", 8);
        }

        return "member/myFavorite";
    }
}
