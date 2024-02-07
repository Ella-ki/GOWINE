package com.gowine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wineries")
public class Winery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "winery_name")
    private String wineryName;
}
