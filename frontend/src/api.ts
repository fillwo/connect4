import axios, { AxiosInstance } from "axios";
import { BASE_URL } from "./config";

type Game = {
  turn: number;
  finished: boolean;
  playerOne: { id: number };
  playerTwo: { id: number };
};

export type NewRoom = {
  id: string;
  game: Game;
};

export type RoomDto = {
  roomId: string;
  playerId: string;
  playerNum: number;
  yourTurn: boolean;
  finished: boolean;
  boardStatus: number[][];
  gameState: "ONGOING" | "DRAW" | "WON" | "LOST";
  winningChipPositions: number[][];
};

class Api {
  axiosInstance: AxiosInstance;

  constructor() {
    this.axiosInstance = axios.create({
      baseURL: BASE_URL,
    });
  }

  createRoom() {
    return this.axiosInstance.post<NewRoom>("/rooms/create");
  }

  getRoom(roomId: string, playerId: string) {
    return this.axiosInstance.get<RoomDto>(`/rooms/${roomId}/${playerId}`);
  }

  addChip(roomId: string, playerId: string, posX: number) {
    return this.axiosInstance.post<RoomDto>("/rooms/play", {
      roomId,
      playerId,
      posX,
    });
  }
}

const api = new Api();

export default api;
