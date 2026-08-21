package com.luke.personal.gameofthegoose.turns;

import com.luke.personal.gameofthegoose.Player;

import java.util.List;

public class TurnOrder {

    private final List<Player> roundOrder;
    private List<Player> currentRound;
    private List<Player> nextRound;

    public Player nextPlayer() {

    }

    public void nextRound() {
        this.currentRound = this.nextRound;
    }

    public void skip(Player player, int rounds) {

    }

    public void skip(Player player) {
        this.skip(player, 1);
    }
}
