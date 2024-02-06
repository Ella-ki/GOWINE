package com.gowine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wine_styles")
@Getter
@Setter
public class WineStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sweetness_percent")
    private int sweetnessPercent;

    @Column(name = "acidity_percent")
    private int acidityPercent;

    @Column(name = "body_percent")
    private int bodyPercent;

    @Column(name = "tannin_percent")
    private int tanninPercent;
}
