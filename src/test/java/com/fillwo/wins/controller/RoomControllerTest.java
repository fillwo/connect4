package com.fillwo.wins.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fillwo.wins.dto.PlayDto;
import com.fillwo.wins.model.Room;
import com.fillwo.wins.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomService roomService;

    @Test
    public void createRoom() throws Exception {
        assertEquals(0, this.roomService.getAll().size());
        this.mockMvc.perform(post("/rooms/create"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.game.turn").value(0))
                .andExpect(jsonPath("$.game.finished").value(false));
        assertEquals(1, this.roomService.getAll().size());
        this.roomService.deleteAllRooms();
    }

    @Test
    public void playGameMissingArgs() throws Exception {
        this.mockMvc.perform(post("/rooms/play"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void playGame() throws Exception {
        assertEquals(0, this.roomService.getAll().size());
        this.mockMvc.perform(post("/rooms/create"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.game.turn").value(0))
                .andExpect(jsonPath("$.game.finished").value(false));
        assertEquals(1, this.roomService.getAll().size());

        Room room = this.roomService.getAll().get(0);
        PlayDto payload = new PlayDto();
        payload.setRoomId(room.getId());
        payload.setPlayerId(room.getGame().getPlayerOne().getId());
        payload.setPosX(5);

        // place first chip
        this.mockMvc.perform(
                post("/rooms/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload))
            ).andExpect(status().isOk()); // .andDo(print());

        // try to play again, but not your turn
        this.mockMvc.perform(
                post("/rooms/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload))
        ).andExpect(status().isMethodNotAllowed());

        // wrong roomId
        payload.setRoomId("wrong-room-id");
        this.mockMvc.perform(
                post("/rooms/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload))
        ).andExpect(status().isNotFound());

        // wrong playerId
        payload.setRoomId(room.getId());
        payload.setPlayerId("wrong-player-id");
        this.mockMvc.perform(
                post("/rooms/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload))
        ).andExpect(status().isNotFound());

        this.roomService.deleteAllRooms();
    }
}