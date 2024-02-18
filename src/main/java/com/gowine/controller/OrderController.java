package com.gowine.controller;

import com.gowine.dto.CartOrderDto;
import com.gowine.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final CartService cartService;

    /*
    @PostMapping(value = "/payment")
    public @ResponseBody ResponseEntity paymentList(@RequestBody CartOrderDto cartOrderDto, Principal principal){


    }
    */

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {
        System.out.println(cartOrderDto.getCartItemId());
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        // 1. 유효성 검사 후
        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

        for(CartOrderDto cartOrder : cartOrderDtoList){
            // 카트 안에 들어있는 cartId 와 로그인한 유저의 id 동일한지 유효성 검사
            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        // 2. cartService
        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
