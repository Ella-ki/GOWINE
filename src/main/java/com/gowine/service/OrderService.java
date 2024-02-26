package com.gowine.service;

import com.gowine.dto.OrderDto;
import com.gowine.dto.OrderHistDto;
import com.gowine.dto.OrderItemDto;
import com.gowine.entity.*;
import com.gowine.repository.ItemImgRepository;
import com.gowine.repository.ItemRepository;
import com.gowine.repository.MemberRepository;
import com.gowine.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email).orElseThrow();

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

    // Entity Order, OrderItem
    // change OrderHistDto, OrderItemDto -> result  Page<OrderHistDto>
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) { //OrderHistDto 리스트 만드는 메서드
        List<Order> orders = orderRepository.findOrders(email, pageable); // 전체 주문 리스트
        Long totalCount = orderRepository.countOrder(email); // 전체 주문 개수
        List<OrderHistDto> orderHistDtos = new ArrayList<>(); // OrderHistDto List 객체 생성
        // Order -> OrderHistDto
        // OrderItem -> OrderItemDto
        for (Order order : orders) { // orders => order null 될 때까지
            OrderHistDto orderHistDto = new OrderHistDto(order); // Order => OrderHistDto
            List<OrderItem> orderItems = order.getOrderItems(); // Order <= List<OrderItem> 추출
            for (OrderItem orderItem : orderItems) { // orderItems -> orderItem 추출 null 될 때까지
                // orderItem, id, Y => 이용해서 이미지 객체를 추출
                ItemImg itemImg = itemImgRepository.findByItemId(orderItem.getItem().getId());
                // 객체를 생성할 때 orderItem 객체와 itemImg URL을 매개변수로 받음
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                // orderHistDto <- List<orderItemDto> <- orderItemDto를 추가
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            // orderHistDtos(List<OrderHist>) 위에 orderHistDto를 추가
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount); // 페이지 단위로 orderHistDtos, pageable, totalCount 넘겨줌
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getAllOrders(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdBy");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Order> orderList = orderRepository.findAll(pageable);
        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orderList) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();

            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemId(orderItem.getItem().getId());
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<>(orderHistDtos, pageable, orderList.getTotalElements());
    }


    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email).orElseThrow(); // 현재 로그인된 member
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new); // id에 맞는 주문서
        Member savedMember = order.getMember(); // order에 있는 member

        // 현재 로그인된 Member 와 order에 있는 member의 이메일이 같은지 비교
        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }
        return true;
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new); // orderId인 주문서 추출
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(); // email Member 객체 추출

        List<OrderItem> orderItemList = new ArrayList<>(); // List OrderItem 객체 생성

        // OrderDtoList -> OrderDto 하나씩 빼서 명령문 실행 null 될때까지 반복
        for(OrderDto orderDto : orderDtoList) {
            // OrderDto.itemid => item 추출
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            // Item => OrderDto => Count 를 매개변수로 대입해서 OrderItem 객체를 생성
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            // OrderItemList OrderItem 객체 추가
            orderItemList.add(orderItem);
        }
        
        // Order 객체 생성 -> member.orderItemList
        Order order = Order.createOrder(member, orderItemList);
        // Order save -> 실제 DB에 저장
        orderRepository.save(order);
        // 주문 ID 를 리턴
        return order.getId();
    }

}









