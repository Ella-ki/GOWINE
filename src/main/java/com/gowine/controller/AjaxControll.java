package com.gowine.controller;

import com.gowine.dto.ItemSearchDto;
import com.gowine.dto.MainItemDto;
import com.gowine.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AjaxControll {
    @Autowired
    ItemService itemService;

    @PostMapping(value = "/updateSellStatus")
    public ResponseEntity<String> updateStatus(@RequestParam("itemId") String id) {

        try{
            itemService.updateStatus(Long.valueOf(id));
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.ok("ERROR");
        }
    }

    @GetMapping(value = "/searchResult")
    public void searchItem(ItemSearchDto itemSearchDto, Model model,@RequestParam("keyword") String keyword){
        System.out.println("keyword : " + keyword);

        if(itemSearchDto.getSearchQuery() == null){ // 서치 데이터 x
            itemSearchDto.setSearchQuery(""); // 빈 문자열로 변환
        }
        List<MainItemDto> items = itemService.getSearchItem(itemSearchDto);
        System.out.println(items.size()+"!!!!!!!!!");

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);

        System.out.println("items : " + items);
        System.out.println("itemSearchDto : " + itemSearchDto);

    }
}
