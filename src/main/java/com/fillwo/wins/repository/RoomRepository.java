package com.fillwo.wins.repository;

import com.fillwo.wins.model.Room;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends ListCrudRepository<Room, Long> {
    @Query("SELECT * FROM room WHERE id = :roomId")
    List<Room> findByRoomId(@Param("roomId") String roomId);
}
