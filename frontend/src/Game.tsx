import React, { FC, useEffect, useState, useCallback } from "react";
import { useParams } from "react-router-dom";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import api, { RoomDto } from "./api";
import Board from "./Board";
import useStompClient from "./hooks/useStompClient";
import useSubscription from "./hooks/useSubscription";

type GameStateProps = {
  gameState: RoomDto;
};

const GameStateMessage: FC<GameStateProps> = ({ gameState }) => {
  switch (gameState.gameState) {
    case "ONGOING":
      if (gameState.yourTurn) {
        return <h2> It's your turn! </h2>;
      } else {
        return <h2> Waiting for opponent's move ...</h2>;
      }
    case "DRAW":
      return <h2> Game finished, it's a draw!</h2>;
    case "WON":
      return <h2> Game finished, you have won!</h2>;
    case "LOST":
      return <h2> Game finished, you have lost!</h2>;
  }
};

const Game: FC = () => {
  const [gameState, setGameState] = useState<RoomDto | null>(null);

  const { roomId, playerId } = useParams();

  const { client } = useStompClient();

  const roomUpdateCallback = useCallback((res: RoomDto) => {
    console.log("got roomUpdate!!", res);
    setGameState(res);
  }, []);

  useSubscription(
    client,
    playerId ? `/topic/${playerId}` : null,
    roomUpdateCallback
  );

  const addChip = async (posX: number) => {
    if (gameState) {
      const response = await api.addChip(
        gameState.roomId,
        gameState.playerId,
        posX
      );
      if (response.status === 200) {
        setGameState(response.data);
      } else {
        console.warn("error addChip", response);
      }
    }
  };

  useEffect(() => {
    if (roomId && playerId) {
      api.getRoom(roomId, playerId).then((response) => {
        if (response.status === 200) {
          console.log(response.data);
          setGameState(response.data);
        } else {
          console.warn("could not get room", response);
        }
      });
    }
  }, [roomId, playerId]);

  return (
    <div style={{ padding: "0px 48px 0px 48px" }}>
      {gameState && (
        <DndProvider backend={HTML5Backend}>
          <div style={{ display: "flex", flexDirection: "column" }}>
            <div
              style={{ marginLeft: "auto", marginRight: "auto", width: "60vh" }}
            >
              <h1>Connect 4</h1>
              <GameStateMessage gameState={gameState}></GameStateMessage>
              <Board
                boardState={gameState.boardStatus}
                yourTurn={gameState.yourTurn}
                playerNum={gameState.playerNum}
                addChip={addChip}
              ></Board>
            </div>
          </div>
        </DndProvider>
      )}
    </div>
  );
};

export default Game;
