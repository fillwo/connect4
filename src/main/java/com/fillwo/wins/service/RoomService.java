package com.fillwo.wins.service;

import com.fillwo.wins.game_logic.GameException;
import com.fillwo.wins.model.Room;

import java.util.List;

public interface RoomService {

    public Room createRoom();

    public Room getRoomById(String id);

    public List<Room> getAll();

    public Room addChip(String roomId, String playerId, int posX) throws GameException;

    public void deleteAllRooms();

}
