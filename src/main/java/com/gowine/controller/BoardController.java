package com.gowine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {
    @GetMapping(value = "/board/community")
    public String boardList(){
        return "board/communityList";
    }

    @GetMapping(value = "/board/notice")
    public String noticeList(){
        return "board/noticeList";
    }
}
