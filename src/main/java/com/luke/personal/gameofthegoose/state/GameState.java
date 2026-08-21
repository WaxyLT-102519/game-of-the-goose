package com.luke.personal.gameofthegoose.state;

import com.luke.personal.gameofthegoose.board.BoardSpace;

public record GameState(
        PlayerPositions beforeDiceRoll,
        int playerTurn,
        int diceRollValue,
        BoardSpace playerDestination,
        PlayerPositions afterSpaceEffect
) {
}
