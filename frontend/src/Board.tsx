import React, { FC } from "react";
import "./Board.css";

type BoardProps = {
  boardState: number[][];
  yourTurn: boolean;
  playerNum: number;
  addChip?: (posX: number) => void;
};

const Board: FC<BoardProps> = ({
  boardState,
  yourTurn,
  playerNum,
  addChip = () => {},
}) => {
  return (
    <div>
      {yourTurn && (
        <div style={{ display: "flex" }}>
          {boardState[0].map((_, i) => {
            return (
              <div
                className={`enter enter${playerNum}`}
                key={`enter-${i}`}
                onClick={() => addChip(i)}
              ></div>
            );
          })}
        </div>
      )}
      {boardState.map((row, i) => {
        return (
          <div className="row" key={`row-${i}`}>
            {row.map((field, j) => (
              <div className="field" key={`field-${i}-${j}`}>
                <div className={`circle circle${field}`}></div>
              </div>
            ))}
          </div>
        );
      })}
    </div>
  );
};

export default Board;
