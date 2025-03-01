package com.trivia.trivia.game.Entity;


public enum Category {
    SPORT("ספורט"),
    ACTUALITY("אקטואליה"),
    COUNTRIES("מדינות"),
    POLITICS("פוליטיקה"),
    FLAGS("דגלים"),
    ENTERTAINMENT("בידור"),
    HISTORY("היסטוריה"),
    SCIENCE("מדע");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
