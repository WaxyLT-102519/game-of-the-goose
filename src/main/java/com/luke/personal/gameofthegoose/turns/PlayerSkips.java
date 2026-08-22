package com.luke.personal.gameofthegoose.turns;

import com.luke.personal.gameofthegoose.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerSkips {

    private final Map<Player, Integer> skipCounters;

    public PlayerSkips(List<Player> players) {
        this.skipCounters = players.stream()
                .collect(Collectors.toMap(player -> player, player -> 0));
    }

    public void skipTurns(Player player, int turns) {
        int turnsToSkip = skipCounters.get(player) + turns;
        skipCounters.put(player, turnsToSkip);
    }

    public boolean canPlay(Player player) {
        return skipCounters.get(player) == 0;
    }

    public void wait(Player player) {
        int turnsToSkip = skipCounters.get(player);

        if (turnsToSkip > 0) {
            turnsToSkip--;
        }

        skipCounters.put(player, turnsToSkip);
    }
}
