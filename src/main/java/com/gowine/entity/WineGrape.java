package com.gowine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wine_grapes")
@Getter
@Setter
public class WineGrape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grape_name")
    private String grapeName;
}
