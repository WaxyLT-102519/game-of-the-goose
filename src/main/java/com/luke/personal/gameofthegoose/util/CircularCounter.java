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

    public long range() {
        return ((long) maxExclusive) - ((long) minInclusive);
    }

    public int next() {
        int nextValue;

        if (current == maxExclusive) {
            nextValue = minInclusive;
        } else {
            nextValue = current;
            current++;
        }

        return nextValue;
    }


    public String toString() {
        return "(%d, %d, %d)".formatted(minInclusive, current, maxExclusive);
    }

    public static CircularCounter fromZeroTo(int maxExclusive) {
        return CircularCounter.from(0).to(maxExclusive);
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
            long range = ((long) maxExclusive) - ((long) minInclusive);
            if (range > Integer.MAX_VALUE) {
                throw new IllegalStateException("Cannot count within a range greater than Integer.MAX_VALUE");
            }

            return new CircularCounter(minInclusive, maxExclusive);
        }
    }
}
