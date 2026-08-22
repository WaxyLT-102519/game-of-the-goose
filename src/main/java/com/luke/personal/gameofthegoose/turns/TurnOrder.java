package com.luke.personal.gameofthegoose.turns;

import com.luke.personal.gameofthegoose.Player;
import com.luke.personal.gameofthegoose.util.CircularCounter;

import java.util.List;

public class TurnOrder {

    private final List<Player> players;
    private final CircularCounter counter;
    private final PlayerSkips playerSkips;

    public TurnOrder(List<Player> players) {
        this.players = List.copyOf(players);
        this.counter = CircularCounter.from(0).to(players.size());
        this.playerSkips = new PlayerSkips(players);
    }

    public Player next() {
        int playerIndex = counter.next();
        Player toPlay = players.get(playerIndex);

        if (playerSkips.canPlay(toPlay)) {
            return toPlay;
        } else {
            playerSkips.wait(toPlay);
            return this.next();
        }
    }

    public void skipPlayer(Player player, int turns) {
        playerSkips.skipTurns(player, turns);
    }
}
