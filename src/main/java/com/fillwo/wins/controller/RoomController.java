package com.fillwo.wins.controller;

import com.fillwo.wins.dto.PlayDto;
import com.fillwo.wins.dto.RoomDto;
import com.fillwo.wins.game_logic.GameException;
import com.fillwo.wins.model.Room;
import com.fillwo.wins.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RoomController {
    private final RoomService roomService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public RoomController(RoomService roomService, SimpMessagingTemplate simpMessagingTemplate) {
        this.roomService = roomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/rooms/{roomId}/{playerId}")
    public RoomDto getRoom(@PathVariable("roomId") String roomId, @PathVariable("playerId") String playerId) {
        Room room = this.roomService.getRoomById(roomId);
        boolean isPlayerOne = playerId.equals(room.getGame().getPlayerOne().getId());
        boolean isPlayerTwo = playerId.equals(room.getGame().getPlayerTwo().getId());
        if (!isPlayerOne && !isPlayerTwo) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        return new RoomDto(playerId, room);
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
    public RoomDto roomPlay(@RequestBody PlayDto payload) {
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

        // send websocket update to other client
        String opponentId;
        if (isPlayerOne) {
            opponentId = room.getGame().getPlayerTwo().getId();
        } else {
            opponentId = room.getGame().getPlayerOne().getId();
        }

        this.simpMessagingTemplate.convertAndSend(
                "/topic/" + opponentId,
                new RoomDto(opponentId, room)
        );

        return new RoomDto(playerId, room);
    }
}
