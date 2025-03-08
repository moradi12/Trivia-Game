package com.trivia.trivia.game.Entity;


public enum Category {
    SPORT("ספורט"),
    COUNTRIES("מדינות"),
    ENTERTAINMENT("בידור"),
    HISTORY("היסטוריה"),
    SCIENCE("מדע"),
    GEOGRAPHY ("גאוגרפיה");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
