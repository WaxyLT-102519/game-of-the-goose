package com.luke.personal.gameofthegoose.util;

public class Die {

    private static final int MAX_ROLL = 6;

    public int roll() {
        return (int) (Math.random() * MAX_ROLL) + 1;
    }
}
