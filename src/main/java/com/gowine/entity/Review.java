package com.gowine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item_reviews")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "review_text", length = 1000)
    private String reviewText;

    @Column(name = "rating")
    private int rating;

    @Column(name = "reviewer_name")
    private String reviewerName;

}
