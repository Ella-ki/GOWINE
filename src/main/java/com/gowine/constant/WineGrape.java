package com.gowine.constant;

public enum WineGrape {
    SHIRAZ("시라/쉬라즈"),
    CABERNET_SAUVIGNON("까베르네 쇼비뇽"),
    SAUVIGNON_BLANC("쇼비뇽 블랑"),
    CHARDONNAY("샤도네이"),
    MALBAC("말백"),
    MERLOT("멜롯"),
    PINOT_NOIR("피노누아"),
    TEMPRANILLO("템프라니요"),
    ZINFANDEL("진판델"),
    PRIMITIVO("퍼미티보"),
    RIESLING("리즐링"),
    BLEND("블렌드"),
    ETC("기타");

    private String displayName;

    WineGrape(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
