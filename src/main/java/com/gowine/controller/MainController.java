package com.gowine.controller;

import com.gowine.dto.ItemSearchDto;
import com.gowine.dto.MainItemDto;
import com.gowine.service.CartService;
import com.gowine.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemService itemService;

    @GetMapping(value = "/")
    public String main(Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(0), 8);
        Page<MainItemDto> items = itemService.getMainItemPage(pageable);

        model.addAttribute("items", items);
        model.addAttribute("maxPage", 8);
        return "index";
    }
}
