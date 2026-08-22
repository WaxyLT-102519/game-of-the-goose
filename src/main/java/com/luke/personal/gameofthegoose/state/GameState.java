package com.luke.personal.gameofthegoose.state;

import com.luke.personal.gameofthegoose.Player;
import com.luke.personal.gameofthegoose.board.BoardSpace;

public record GameState(
        PlayerPositions beforeDiceRoll,
        Player toPlay,
        int diceRollValue,
        BoardSpace playerDestination,
        PlayerPositions afterSpaceEffect
) {
}
