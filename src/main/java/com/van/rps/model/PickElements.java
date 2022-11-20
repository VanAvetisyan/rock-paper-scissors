package com.van.rps.model;

public enum PickElements {
    ROCK("rock"),
    PAPER("paper"),
    SCISSORS("scissors");

    private final String value;

    PickElements(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
