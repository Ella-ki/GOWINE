package com.gowine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WineStyleDto {
    private Long id;
    private int sweetnessPercent;
    private int acidityPercent;
    private int bodyPercent;
    private int tanninPercent;
}
