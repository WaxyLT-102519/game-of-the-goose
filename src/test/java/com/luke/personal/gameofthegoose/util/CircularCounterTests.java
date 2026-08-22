package com.luke.personal.gameofthegoose.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for CircularCounter")
public class CircularCounterTests {

    private static Stream<Arguments> allUniqueValueTypes() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(1),
                Arguments.of(-1),
                Arguments.of(42),
                Arguments.of(-42),
                Arguments.of(Integer.MIN_VALUE),
                Arguments.of(Integer.MAX_VALUE)
        );
    }

    @Nested
    @DisplayName("Tests for range")
    class RangeTests {

        @ParameterizedTest
        @MethodSource("supply_shouldReturn0_whenMinInclusiveAndMaxExclusiveAreTheSame")
        @DisplayName("Should return 0 when minInclusive and maxExclusive are the same")
        void shouldReturn0_whenMinInclusiveAndMaxExclusiveAreTheSame(
                int bounds
        ) {
            var counter = CircularCounter.from(bounds).to(bounds);

            assertThat(counter.range()).isZero();
        }

        private static Stream<Arguments> supply_shouldReturn0_whenMinInclusiveAndMaxExclusiveAreTheSame() {
            return allUniqueValueTypes();
        }
    }
    
    @Nested
    @DisplayName("Tests for next")
    class NextTests {

        @ParameterizedTest
        @MethodSource("supply_shouldAlwaysReturnTheSameNumber_whenMinInclusiveAndMaxExclusiveAreTheSame")
        @DisplayName("Should always return the same number when minInclusive and maxExclusive are the same")
        void shouldAlwaysReturnTheSameNumber_whenMinInclusiveAndMaxExclusiveAreTheSame(
                int bounds
        ) {
            var counter = CircularCounter.from(bounds).to(bounds);

            assertThat(counter.next()).isEqualTo(bounds);
            assertThat(counter.next()).isEqualTo(bounds);
            assertThat(counter.next()).isEqualTo(bounds);
        }

        private static Stream<Arguments> supply_shouldAlwaysReturnTheSameNumber_whenMinInclusiveAndMaxExclusiveAreTheSame() {
            return allUniqueValueTypes();
        }

        @ParameterizedTest
        @MethodSource("supply_shouldReturnMinInclusive_whenNextIsCalledForTheFirstTime")
        @DisplayName("Should return minInclusive when next is called for the first time")
        void shouldReturnMinInclusive_whenNextIsCalledForTheFirstTime(
                int minInclusive
        ) {
            var counter = CircularCounter.from(minInclusive).to(Integer.MAX_VALUE);

            assertThat(counter.next()).isEqualTo(minInclusive);
        }

        private static Stream<Arguments> supply_shouldReturnMinInclusive_whenNextIsCalledForTheFirstTime() {
            return allUniqueValueTypes();
        }
        
        @ParameterizedTest
        @MethodSource("supply_shouldReturnMinInclusive_whenCounterResets")
        @DisplayName("Should return minInclusive when counter resets")
        void shouldReturnMinInclusive_whenCounterResets(
                int minInclusive,
                int maxExclusive
        ) {
            var counter = CircularCounter.from(minInclusive).to(maxExclusive);
            long range = counter.range();

            assertThat(counter.next()).isEqualTo(minInclusive);
            for (long i = 0; i < range; i++) {
                int debug = counter.next(); // this variable is only stored for debugging purposes
                String debugString = "The counter is at: %d".formatted(debug);
            }
            assertThat(counter.next()).isEqualTo(minInclusive);
        }
        
        private static Stream<Arguments> supply_shouldReturnMinInclusive_whenCounterResets() {
            return Stream.of(
                    Arguments.of(0, 0),
                    Arguments.of(0, 1),
                    Arguments.of(0, 4),
                    Arguments.of(2, 4),
                    Arguments.of(Integer.MIN_VALUE, Integer.MAX_VALUE)
            );
        }
    }
}
