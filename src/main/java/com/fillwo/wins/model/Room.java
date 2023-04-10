package com.fillwo.wins.model;

import com.fillwo.wins.game_logic.Game;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Embedded;

import java.util.UUID;

public class Room {

    @Id
    private Long idNum;
    private final String id;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private final Game game;

    public Room() {
        this.id = String.valueOf(UUID.randomUUID());
        this.game = new Game();
    }

    @PersistenceCreator
    public Room(Long idNum, String id, Game game) {
        this.idNum = idNum;
        this.id = id;
        this.game = game;
    }

    public String getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Long getIdNum() {
        return idNum;
    }

    public void setIdNum(Long idNum) {
        this.idNum = idNum;
    }

    @Override
    public String toString() {
        return "Room{" +
                "idNum=" + idNum +
                ", id='" + id + '\'' +
                ", game=" + game +
                '}';
    }
}
