package com.gowine.controller;

import com.gowine.dto.MainItemDto;
import com.gowine.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemRestController {
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
    public List<MainItemDto> searchItem(@RequestParam("keyword") String keyword){
        List<MainItemDto> items = itemService.getSearchItem(keyword);
        return items;
    }

    @GetMapping(value = "/searchMbtiItem")
    public List<MainItemDto> mbtiItem(@RequestParam("mbti") String mbti) {
        List<MainItemDto> items = itemService.getMbtiItem(mbti);
        return items;
    }
}
