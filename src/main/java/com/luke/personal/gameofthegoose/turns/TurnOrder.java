package com.luke.personal.gameofthegoose.turns;

import com.luke.personal.gameofthegoose.Player;
import com.luke.personal.gameofthegoose.util.CircularCounter;

import java.util.ArrayList;
import java.util.List;

public class TurnOrder {

    private final List<Player> roundOrder;
    private final CircularCounter circularCounter;

    private List<Player> currentRound;
    private List<Player> nextRound;

    public TurnOrder(List<Player> roundOrder) {
        this.roundOrder = List.copyOf(roundOrder);
        this.circularCounter = CircularCounter.from(0).to(roundOrder.size());

        this.currentRound = new ArrayList<>(roundOrder);
        this.nextRound = new ArrayList<>(roundOrder);
    }

    public Player nextPlayer() {
        return currentRound.get(circularCounter.next());
    }

    public void nextRound() {
        this.currentRound = new ArrayList<>(this.nextRound);
        this.nextRound = new ArrayList<>(this.roundOrder);
    }

    public void skip(Player player, int rounds) {
        // todo: implement so that you can skip 1, 2, or n rounds
    }

    public void skip(Player player) {
        this.skip(player, 1);
    }
}
