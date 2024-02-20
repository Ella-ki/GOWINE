package com.gowine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "item_reviews")
@Getter
@Setter
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

    @Column(name = "review_text", length = 1000)
    private String reviewText;

    @Column(name = "review_rate")
    private Double reviewRate;

    @Column(name = "reviewer_name")
    private String reviewerName;

}
