package com.gowine.service;

import com.gowine.dto.CartDetailDto;
import com.gowine.dto.CartItemDto;
import com.gowine.dto.CartOrderDto;
import com.gowine.dto.OrderDto;
import com.gowine.entity.Cart;
import com.gowine.entity.CartItem;
import com.gowine.entity.Item;
import com.gowine.entity.Member;
import com.gowine.repository.CartItemRepository;
import com.gowine.repository.CartRepository;
import com.gowine.repository.ItemRepository;
import com.gowine.repository.MemberRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCart(@Valid CartItemDto cartItemDto, String email) {
        // item 결과 값 받기                                           null 나올 수 있으니 orElseThrow 써줘야함
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        // member 결과 값 받기
        Member member = memberRepository.findByEmail(email).orElseThrow();

        // member id 를 이용해서 Cart 결과 값 받기
        Cart cart = cartRepository.findByMemberId(member.getId());
        // Cart 객체가 null 이면 실행 -> 최신 회원
        if (cart == null) {
            // Cart 생성
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // Cart id, item id 를 조회 CartItem을 결과 값 받기
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        // CartItem 결과 값이 객체가 있는 경우 => 동일한 상품 있으면 수량만 증가
        if (savedCartItem != null) {
            // 수량만 증가 -> 변경감지
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else { // CartItem 결과 값이 객체가 없는 경우 => 동일한 상품 없으면 상품 객체 추가

            // CartItem 객체 생성 후 -> save DB에 저장
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }

    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){
        // CartDetailDto List 객체 생성
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();
        
        // 현재 로그인된 이메일로 Member 추출
        Member member = memberRepository.findByEmail(email).orElseThrow();
        
        // Member id를 이용해서 Cart 추출
        Cart cart = cartRepository.findByMemberId(member.getId());
        
        // Cart가 null 이면 return 아무것도 없는 CartDetailDto List 객체 추출
        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());

        return cartDetailDtoList;
    }


    @Transactional(readOnly = true)
    public int getCartCout(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        Cart cart = cartRepository.findByMemberId(member.getId());

        if(cart == null) {
            return 0;
        }

        int CartCount = cartItemRepository.getTotalCountByCartId(cart.getId());
        return CartCount;
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email) {
        Member curMember = memberRepository.findByEmail(email).orElseThrow();
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityExistsException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }
        return true;
    }

    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityExistsException::new);
        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId) {
        // CartItem 이용해서 CartItem 추출
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityExistsException::new);
        // 추출된 CartItem 을 cartItemRepository 를 이용해서 삭제
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {
        List<OrderDto> orderDtoList = new ArrayList<>(); // List 주문 Dto 객체 생성
        for(CartOrderDto cartOrderDto : cartOrderDtoList) {
            // CartOrderDto.CartItemId => CartItemId 추출
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityExistsException::new);
            OrderDto orderDto = new OrderDto(); // OrderDto 객체 생성
            orderDto.setItemId(cartItem.getItem().getId()); // CartItem.item.id -> OrderDto.itemId
            orderDto.setCount(cartItem.getCount()); // CartItem.count -> OrderDto.Count
            orderDtoList.add(orderDto); // List<OrderDto>에 추가
        }

        // 주문 OrderService orders 호출 orderDtoList, email
        Long orderId = orderService.orders(orderDtoList, email);

        // CartOrderDto List -> CartOrderDto를 하나씩 빼고 null 될때까지 반복
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            // CartOrderDto -> CartItemId -> CartItem 객체를 추출
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityExistsException::new);
            // CartItem을 삭제 -> 실제 DB에서도 삭제
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }

}








