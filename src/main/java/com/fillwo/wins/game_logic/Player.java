package com.fillwo.wins.game_logic;

import java.util.UUID;

public class Player {

    private String id;

    public Player() {
        this.id = String.valueOf(UUID.randomUUID());
    }

    public Player(String id) {
        this.id = id;
    }

    public Player(UUID id) {
        this.id = String.valueOf(id);
    }

    public String getId() {
        return id;
    }
}
