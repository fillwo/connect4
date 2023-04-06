package com.fillwo.wins.controller;

import com.fillwo.wins.game_logic.GameException;
import com.fillwo.wins.model.Room;
import com.fillwo.wins.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

class PlayRequestPayload {
    private String roomId;
    private String playerId;
    private int posX;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
}

@RestController
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms/{roomId}/{playerId}")
    public PersonalRoomResponse getRoom(@PathVariable("roomId") String roomId, @PathVariable("playerId") String playerId) {
        Room room = this.roomService.getRoomById(roomId);
        boolean isPlayerOne = playerId.equals(room.getGame().getPlayerOne().getId());
        boolean isPlayerTwo = playerId.equals(room.getGame().getPlayerTwo().getId());
        if (!isPlayerOne && !isPlayerTwo) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        return new PersonalRoomResponse(playerId, room);
    }

    @GetMapping("/rooms/all")
    public List<Room> getRooms() {
        return this.roomService.getAll();
    }

    @PostMapping("/rooms/create")
    public Room createRoom() {
        return this.roomService.createRoom();
    }

    @PostMapping("/rooms/play")
    public PersonalRoomResponse roomPlay(@RequestBody PlayRequestPayload payload) {
        String roomId = payload.getRoomId();
        String playerId = payload.getPlayerId();
        int posX = payload.getPosX();

        Room room = this.roomService.getRoomById(roomId);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        boolean isPlayerOne = playerId.equals(room.getGame().getPlayerOne().getId());
        boolean isPlayerTwo = playerId.equals(room.getGame().getPlayerTwo().getId());
        if (!isPlayerOne && !isPlayerTwo) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        try {
            this.roomService.addChip(roomId, playerId, posX);
        } catch (GameException e) {
            System.out.println("GameException " + e);
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
        }

        return new PersonalRoomResponse(playerId, room);
    }
}