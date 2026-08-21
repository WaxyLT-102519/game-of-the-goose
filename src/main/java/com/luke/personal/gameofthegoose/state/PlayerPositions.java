package com.luke.personal.gameofthegoose.state;

import com.luke.personal.gameofthegoose.Player;
import com.luke.personal.gameofthegoose.board.BoardSpace;
import com.luke.personal.gameofthegoose.board.GameBoard;

import java.util.HashMap;
import java.util.Map;

public class PlayerPositions {

    private final Map<Player, Integer> positions;
    private final GameBoard gameBoard;

    public PlayerPositions(Map<Player, Integer> positions, GameBoard gameBoard) {
        this.positions = Map.copyOf(positions);
        this.gameBoard = gameBoard;
    }

    public BoardSpace getPlayerPosition(Player player) {
        int boardIndex = positions.get(player);

        return gameBoard.spaceAt(boardIndex);
    }

    public PlayerPositions move(Player player, int numberOfSpaces) {
        int destinationIndex = positions.get(player) + numberOfSpaces;

        var newPositions = new HashMap<>(this.positions);
        newPositions.put(player, destinationIndex);

        return new PlayerPositions(newPositions, gameBoard);
    }
}
