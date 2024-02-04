package com.gowine.entity;

import com.gowine.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // persistenceContext 안에 들어가는거임
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // many -> order , one -> JoinColumn
    @JoinColumn(name = "member_id")
    private Member member;

    // 주문서는 주문 아이템을 read 만 할 수 있다. 양방향에서 부하 관계가 order 임
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
                , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //private LocalDateTime regTime;

    //private LocalDateTime updateTime;

    // 주문서 주문아이템 리스트에 주문 아이템 추가
    // 주문 아이템에 주문서 추가
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 주문서 생성
    // 현재 로그인된 멤버 주문서에 추가
    // 주문아이템 리스트를 반복문을 통해서 주문서에 추가
    // 상태는 주문으로 세팅
    // 주문 시간은 현재 시간으로 세팅
    // 주문서 리턴
    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order(); // Order 객체 생성
        order.setMember(member); // Order <- member
        for(OrderItem orderItem : orderItemList){ // Order에 있는 List<OrderItem> 매개변수로 넘어온 orderItemList 데이터를 추가
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER); // Order상태를 ORDER
        order.setOrderDate(LocalDateTime.now()); // Order 현재 시간으로 세팅
        return order; // 주문서 리턴
    }

    // 주문서에 있는 주문 아이템 리스트를 반복
    // 주문 아이템마다 총 가격을 totalPrice에 추가
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL; // 주문서 상태 CANCEL 로 변환
        // Order -> OrderItem List null 이 나올때까지 반복
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
}
