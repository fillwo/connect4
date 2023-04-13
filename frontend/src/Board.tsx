import React, { FC } from "react";
import { useDrag, useDrop } from "react-dnd";

import "./Board.css";

type BoardProps = {
  boardState: number[][];
  yourTurn: boolean;
  playerNum: number;
  addChip?: (posX: number) => void;
};

const DropItem: FC<{
  index: number;
  playerNum: number;
  addChip: (posX: number) => void;
}> = ({ index, playerNum, addChip }) => {
  const [{ isOver }, drop] = useDrop(() => ({
    accept: "chip",
    drop: () => addChip(index),
    collect: (monitor) => ({
      isOver: !!monitor.isOver(),
    }),
  }));

  return (
    <div
      ref={drop}
      className={
        "grid-item-drop " + (isOver ? `circle${playerNum}-drop-area` : "")
      }
    ></div>
  );
};

const Board: FC<BoardProps> = ({
  boardState,
  yourTurn,
  playerNum,
  addChip = () => {},
}) => {
  const [{ isDragging }, drag] = useDrag(() => ({
    type: "chip",
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging(),
    }),
  }));

  return (
    <div>
      <div style={{ visibility: yourTurn ? "visible" : "hidden" }}>
        <div className="grid-container-single">
          <div
            ref={drag}
            className={`grid-item-single circle${playerNum}`}
            style={{ opacity: isDragging ? 0.2 : 1 }}
          ></div>
          <div
            className="drag-me-item"
            style={{ opacity: isDragging ? 0.2 : 1 }}
          >
            <div className="drag-me-text">drag me</div>
          </div>
        </div>
        <div
          className="grid-container-drop"
          style={{ visibility: isDragging ? "visible" : "hidden" }}
        >
          {boardState[0].map((_, idx) => {
            return (
              <DropItem
                key={idx}
                index={idx}
                playerNum={playerNum}
                addChip={addChip}
              ></DropItem>
            );
          })}
        </div>
      </div>

      <div className="grid-container">
        {boardState
          .reduce((acc, curr) => {
            return [...acc, ...curr];
          }, [])
          .map((chip, idx) => {
            return <div className={`grid-item circle${chip}`} key={idx}></div>;
          })}
      </div>
    </div>
  );
};

export default Board;
