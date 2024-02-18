package com.gowine.controller;

import com.gowine.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HeaderController {
    @Autowired
    CartService cartService;


}
