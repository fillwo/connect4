package com.fillwo.wins.service;

import com.fillwo.wins.game_logic.GameException;
import com.fillwo.wins.game_logic.Player;
import com.fillwo.wins.model.Room;
import com.fillwo.wins.repository.RoomRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgres")
public class RoomServiceDB implements RoomService {
    private RoomRepository roomRepository;

    public RoomServiceDB(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room createRoom() {
        Room room = new Room();
        this.roomRepository.save(room);
        return room;
    }

    @Override
    public Room getRoomById(String id) {
        List<Room> rooms = this.roomRepository.findByRoomId(id);
        if (rooms.size() == 0) {
            return null;
        } else {
            return rooms.get(0);
        }
    }

    @Override
    public List<Room> getAll() {
        return this.roomRepository.findAll();
    }

    @Override
    public Room addChip(String roomId, String playerId, int posX) throws GameException {
        List<Room> rooms = this.roomRepository.findByRoomId(roomId);
        Room room = rooms.get(0);
        room.getGame().addChip(new Player(playerId), posX);
        this.roomRepository.save(room);
        return room;
    }

    @Override
    public void deleteAllRooms() {
        this.roomRepository.deleteAll();
    }


}
