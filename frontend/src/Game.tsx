import React, { FC, Fragment, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api, { RoomDto } from "./api";
import Board from "./Board";

type GameStateProps = {
  gameState: RoomDto;
};

const GameStateMessage: FC<GameStateProps> = ({ gameState }) => {
  switch (gameState.gameState) {
    case "ONGOING":
      if (gameState.yourTurn) {
        return <h3> It's your turn! </h3>;
      } else {
        return <h3> Waiting for opponent's move ...</h3>;
      }
    case "DRAW":
      return <h3> Game finished, it's a draw!</h3>;
    case "WON":
      return <h3> Game finished, you have won!</h3>;
    case "LOST":
      return <h3> Game finished, you have lost!</h3>;
  }
};

const Game: FC = () => {
  const { roomId, playerId } = useParams();

  const [gameState, setGameState] = useState<RoomDto | null>(null);

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
    <div style={{ padding: 48 }}>
      {gameState && (
        <Fragment>
          <h1>Connect 4</h1>
          <GameStateMessage gameState={gameState}></GameStateMessage>
          <Board
            boardState={gameState.boardStatus}
            yourTurn={gameState.yourTurn}
            playerNum={gameState.playerNum}
            addChip={addChip}
          ></Board>
        </Fragment>
      )}
    </div>
  );
};

export default Game;
