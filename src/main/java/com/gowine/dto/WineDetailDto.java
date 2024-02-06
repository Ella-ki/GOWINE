package com.gowine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WineDetailDto {
    private WineGrapeDto wineGrapeDto;
    private WineRegionDto wineRegionDto;
    private WineTypeDto wineTypeDto;
    private WineStyleDto wineStyleDto;
    private WineryDto wineryDto;
    private VivinoRateDto vivinoRateDto;

}
