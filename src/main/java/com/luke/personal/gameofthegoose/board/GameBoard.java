package com.luke.personal.gameofthegoose.board;

import java.util.List;

public class GameBoard {

    private final List<BoardSpace> spaces;

    public GameBoard(List<BoardSpace> spaces) {
        this.spaces = spaces;
    }

    public BoardSpace spaceAt(int position) {
        if (position < 0 || position >= spaces.size()) {
            // todo: create custom exception
            throw new IndexOutOfBoundsException();
        }

        return spaces.get(position);
    }
}
