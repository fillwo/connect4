package com.fillwo.wins.game_logic;

import org.springframework.data.annotation.PersistenceCreator;

import java.util.Objects;
import java.util.UUID;


public class Player {

    private String id;

    public Player() {
        this.id = String.valueOf(UUID.randomUUID());
    }

    public Player(UUID id) {
        this.id = String.valueOf(id);
    }

    @PersistenceCreator
    public Player(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                '}';
    }
}
