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
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/list")
    public String itemList(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(0), 8);

        Page<MainItemDto> items = itemService.getListItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 8);
        return "item/list";
    }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
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
