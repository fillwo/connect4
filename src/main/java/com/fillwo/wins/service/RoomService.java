package com.fillwo.wins.service;

import com.fillwo.wins.game_logic.GameException;
import com.fillwo.wins.game_logic.Player;
import com.fillwo.wins.model.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RoomService {
    HashMap<String, Room> rooms;

    public RoomService() {
        this.rooms = new HashMap<>();
    }

    public Room createRoom() {
        Room room = new Room();
        this.rooms.put(room.getId(), room);
        return room;
    }

    public Room getRoomById(String id) {
        return this.rooms.get(id);
    }

    public List<Room> getAll() {
        return new ArrayList<>(this.rooms.values());
    }

    public void addChip(String roomId, String playerId, int posX) throws GameException {
        Room room = this.rooms.get(roomId);
        room.getGame().addChip(new Player(playerId), posX);

    }

    public void deleteAllRooms() {
        this.rooms.clear();
    }
}
