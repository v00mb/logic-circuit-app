package org.example.model;

public enum Signal {
    LOW(0), HIGH(1), UNDEFINED(-1);

    private final int value;

    Signal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isHigh() {
        return this == HIGH;
    }

    public static Signal from(boolean b) {
        return b
                ? HIGH
                : LOW;
    }
}
