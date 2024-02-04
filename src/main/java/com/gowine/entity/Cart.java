package com.gowine.entity;

import jakarta.persistence.*;
import lombok.Data;

// 단방향 맵핑
// cart 가 member의 member_id 를 외래키로 갖는다
@Entity
@Table(name = "cart")
@Data
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 1:1 맵핑
    @JoinColumn(name = "member_id") // JoinColumn 매핑할 외래기를 지정합니다. 외래키 이름을 설정
    // name 을 명시하지않으면 JPA가 알아서 ID 를 찾지만 원하는 이름이 아닐수도 있습니다.
    private Member member;

    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}
