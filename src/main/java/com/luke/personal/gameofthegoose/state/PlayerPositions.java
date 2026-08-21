package com.luke.personal.gameofthegoose.state;

import com.luke.personal.gameofthegoose.Player;
import com.luke.personal.gameofthegoose.board.BoardSpace;
import com.luke.personal.gameofthegoose.board.GameBoard;

import java.util.Map;

public class PlayerPositions {

    private final Map<Player, Integer> positions;
    private final GameBoard gameBoard;

    public PlayerPositions(Map<Player, Integer> positions, GameBoard gameBoard) {
        this.positions = Map.copyOf(positions);
        this.gameBoard = gameBoard;
    }

    public BoardSpace whereIsPlayer(Player player) {
        int boardIndex = positions.get(player);
        return gameBoard.spaceAt(boardIndex);
    }
}
