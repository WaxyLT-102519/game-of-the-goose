package com.luke.personal.gameofthegoose.util;

public final class CircularCounter {

    private final int minInclusive;
    private final int maxExclusive;
    private int current;

    private CircularCounter(int minInclusive, int maxExclusive) {
        this.minInclusive = minInclusive;
        this.maxExclusive = maxExclusive;
        this.current = minInclusive;
    }

    public int next() {
        current += 1;

        if (current == maxExclusive) {
            current = minInclusive;
        }

        return current;
    }

    public static To from(int minInclusive) {
        return new To(minInclusive);
    }

    public static class To {

        private final int minInclusive;

        public To(int minInclusive) {
            this.minInclusive = minInclusive;
        }

        public CircularCounter to(int maxExclusive) {
            return new CircularCounter(minInclusive, maxExclusive);
        }
    }
}
