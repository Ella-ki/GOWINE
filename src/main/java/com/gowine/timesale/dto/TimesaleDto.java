package com.gowine.timesale.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TimesaleDto {
    private Long itemId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int discountRate;
}
