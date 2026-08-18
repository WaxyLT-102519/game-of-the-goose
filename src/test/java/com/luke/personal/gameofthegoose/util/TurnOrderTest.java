package com.luke.personal.gameofthegoose.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("Tests for TurnOrder")
class TurnOrderTest {

    @Nested
    @DisplayName("Tests for Constructor")
    class ConstructorTests {

        void shouldThrowIllegalArgumentException_whenNumberOfPlayersIsNegative() {
            int negativePlayers = -1;

            assertThatThrownBy(() -> new TurnOrder(negativePlayers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Number of players cannot be negative");
        }
    }

    @Nested
    @DisplayName("Tests for nextTurn()")
    class NextTurnTests {

        void shouldReturnTheNextTurn() {
            int numberOfPlayers = 4;
            TurnOrder turnOrder = new TurnOrder(numberOfPlayers);

            for (int i = 0; i < 100; i++) {
                int turnNumber = i % numberOfPlayers;
                assertThat(turnOrder.nextTurn()).isEqualTo(turnNumber);
            }
        }
    }
}
