package com.gowine.constant;

public enum WineType {
    RED("레드"),
    WHITE("화이트"),
    ROSE("로제"),
    DESSERT("디저트"),
    SPARKLING("스파클링"),
    PORT("포트"),
    ETC("기타");

    private String displayName;

    WineType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
