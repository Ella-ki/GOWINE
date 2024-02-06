package com.gowine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item_ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "score")
    private int score;

}
