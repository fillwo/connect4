package com.fillwo.wins.model;

import com.fillwo.wins.game_logic.Game;

import java.util.UUID;

public class Room {

    private final String id;
    private final Game game;

    public Room() {
        this.id = String.valueOf(UUID.randomUUID());
        this.game = new Game();
    }

    public String getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }
}
