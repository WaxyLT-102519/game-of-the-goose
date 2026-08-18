package com.luke.personal.gameofthegoose.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

public class TurnOrder {

    private final List<Integer> playerReference;
    private final Queue<Integer> nextTurns = new LinkedList<>();

    public TurnOrder(int numberOfPlayers) {
        this.playerReference = IntStream.range(0, numberOfPlayers).boxed().toList();
        this.nextTurns.addAll(this.playerReference);
    }

    private void keepTurnsUpToDate() {
        if (this.nextTurns.isEmpty()) {
            this.nextTurns.addAll(this.playerReference);
        }
    }

    public int nextTurn() {
        keepTurnsUpToDate();
        return nextTurns.poll();
    }

    public void skipPlayer(int playerNumber) {
        int playerIndex = playerNumber - 1;
        List<Integer> turnsWithSkip = new ArrayList<>(this.playerReference);
        turnsWithSkip.remove(playerIndex);
        this.nextTurns.addAll(turnsWithSkip);
    }
}
