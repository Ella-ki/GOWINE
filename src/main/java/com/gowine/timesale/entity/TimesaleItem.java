package com.gowine.timesale.entity;

import com.gowine.entity.Item;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TimesaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endTime;

    private int discountRate;

}
