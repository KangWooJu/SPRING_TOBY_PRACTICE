package org.kangwooju.spring_toby_practice.domain.user.Entity;

import lombok.AllArgsConstructor;

public enum Level {

    GOLD(3), SILVER(2), BRONZE(1);

    private final int value;

    private Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Level fromValue(int value) {
        switch (value) {
            case 3: return GOLD;
            case 2: return SILVER;
            case 1: return BRONZE;
            default: throw new IllegalArgumentException("Unknown value: " + value);
        }
    }
}