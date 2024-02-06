package com.gowine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vivino_ratings")
public class VivinoRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private String rating;
}
