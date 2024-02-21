package com.gowine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "item_reviews")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int grade;

    private String text;

    public void changeGrade(int grade){
        this.grade = grade;
    }

    public void changeText(String text){
        this.text = text;
    }
}
