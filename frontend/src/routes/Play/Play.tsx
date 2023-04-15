import React, { FC, useEffect, useState, useCallback } from "react";
import { useParams } from "react-router-dom";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import { TouchBackend } from "react-dnd-touch-backend";
import api, { RoomDto } from "../../api";
import Board from "./Board";
import useStompClient from "../../hooks/useStompClient";
import useSubscription from "../../hooks/useSubscription";
import useIsTouchScreen from "../../hooks/useIsTouchScreen";
import styles from "./Play.module.css";

type GameStateProps = {
  gameState: RoomDto;
};

const GameStateMessage: FC<GameStateProps> = ({ gameState }) => {
  switch (gameState.gameState) {
    case "ONGOING":
      if (gameState.yourTurn) {
        return <div className={styles.gameMessage}> It's your turn! </div>;
      } else {
        return (
          <div className={styles.gameMessage}>
            Waiting for opponent's move...
          </div>
        );
      }
    case "DRAW":
      return (
        <div className={styles.gameMessage}>Game finished, it's a draw!</div>
      );
    case "WON":
      return (
        <div className={styles.gameMessage}>Game finished, you have won!</div>
      );
    case "LOST":
      return (
        <div className={styles.gameMessage}>Game finished, you have lost!</div>
      );
  }
};

const Play: FC = () => {
  const [gameState, setGameState] = useState<RoomDto | null>(null);

  const isTouch = useIsTouchScreen();

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
    <div>
      {gameState && (
        <DndProvider backend={isTouch ? TouchBackend : HTML5Backend}>
          <div className={styles.contentWrapper}>
            <div className={styles.content}>
              <h1>Connect 4</h1>
              <GameStateMessage gameState={gameState}></GameStateMessage>
              <Board
                boardState={gameState.boardStatus}
                yourTurn={gameState.yourTurn}
                isTouch={isTouch}
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

export default Play;
