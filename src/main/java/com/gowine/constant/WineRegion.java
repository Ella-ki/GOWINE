package com.gowine.constant;

public enum WineRegion {
    FRANCE("프랑스"),
    ITALIA("이탈리아"),
    US("미국"),
    SOUTH_AFRICA("남아공"),
    AUSTRALIA("호주"),
    SPAIN("스페인"),
    GERMAN("독일"),
    CHILE("칠레"),
    ARGENTINA("아르헨티나"),
    PORTUGAL("포르투갈"),
    NEW_ZEALAND("뉴질랜드"),
    ETC("기타");

    private String displayName;

    WineRegion(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
