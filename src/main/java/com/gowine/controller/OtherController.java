package com.gowine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OtherController {
    @GetMapping(value = "/about")
    public String aboutPage(){
        return "others/about";
    }
}
