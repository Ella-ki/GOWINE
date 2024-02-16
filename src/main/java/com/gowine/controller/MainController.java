package com.gowine.controller;

import com.gowine.dto.ItemSearchDto;
import com.gowine.dto.MainItemDto;
import com.gowine.dto.MemberFormDto;
import com.gowine.dto.SocialUser;
import com.gowine.entity.Member;
import com.gowine.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.SocketAddress;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemService itemService;
    @GetMapping(value = "/")
    public String main(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        System.out.println("session: " + session.toString());
        System.out.println("session: " + session.getId());
        return "index";
    }

    /*
    @GetMapping(value = "/") // localhost/호출
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5); // 페이지 유무 확인
        if(itemSearchDto.getSearchQuery() == null){ // 서치 데이터 x
            itemSearchDto.setSearchQuery(""); // 빈 문자열로 변환
        }
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        System.out.println(items.getNumber()+"!!!!!!!!!");
        System.out.println(items.getTotalPages()+"##########");
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "main";
    }
    */
}
