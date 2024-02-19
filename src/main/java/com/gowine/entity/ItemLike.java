package com.gowine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item item;

    public ItemLike(Item item, Member member) {
        this.item = item;
        this.member = member;
    }

}
