package com.fillwo.wins.service;

import com.fillwo.wins.game_logic.GameException;
import com.fillwo.wins.game_logic.Player;
import com.fillwo.wins.model.Room;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Profile("memory")
public class RoomServiceInMemory implements RoomService {
    HashMap<String, Room> rooms;

    public RoomServiceInMemory() {
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

    public Room addChip(String roomId, String playerId, int posX) throws GameException {
        Room room = this.rooms.get(roomId);
        room.getGame().addChip(new Player(playerId), posX);
        return room;
    }

    public void deleteAllRooms() {
        this.rooms.clear();
    }
}
