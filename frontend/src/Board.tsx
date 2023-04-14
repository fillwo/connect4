import React, { FC } from "react";
import { XYCoord, useDrag, useDragLayer, useDrop } from "react-dnd";

import "./Board.css";

type BoardProps = {
  boardState: number[][];
  yourTurn: boolean;
  playerNum: number;
  isTouch: boolean;
  addChip?: (posX: number) => void;
};

// simle grid containing the chips
const Grid: FC<{ boardState: number[][] }> = ({ boardState }) => {
  return (
    <div className="grid-container">
      {boardState
        .reduce((acc, curr) => {
          return [...acc, ...curr];
        }, [])
        .map((chip, idx) => {
          return <div className={`grid-item circle${chip}`} key={idx}></div>;
        })}
    </div>
  );
};

// a chip can be dragged onto a DropItem
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

// compute transform of drag preview item
// only necessary for touch display
const computeItemStyles = (currentOffset: XYCoord | null) => {
  if (!currentOffset) {
    return {
      display: "initial",
    };
  }
  let { x, y } = currentOffset;

  const transform = `translate(${x}px, ${y}px)`;
  return {
    transform,
    WebkitTransform: transform,
  };
};

// custom drag layer for touch display
// otherwise no drag preview would be seen
const CustomDragLayer: FC<{ playerNum: number }> = ({ playerNum }) => {
  const { isDragging, diffOffset } = useDragLayer((monitor) => ({
    isDragging: monitor.isDragging(),
    diffOffset: monitor.getDifferenceFromInitialOffset(),
  }));

  if (!isDragging)
    return (
      <div
        className={`grid-item-single circle${playerNum}`}
        style={{ display: "none", pointerEvents: "none", gridColumnStart: 1 }}
      ></div>
    );

  return (
    <div
      className={`grid-item-single circle${playerNum}`}
      style={{
        ...computeItemStyles(diffOffset),
        pointerEvents: "none",
        gridRowStart: 1,
        gridColumnStart: 6,
      }}
    ></div>
  );
};

// Board consisting of chip to drag, drop area and grid of chips
const Board: FC<BoardProps> = ({
  boardState,
  yourTurn,
  playerNum,
  isTouch,
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
          {isTouch && <CustomDragLayer playerNum={playerNum}></CustomDragLayer>}
          <div
            ref={drag}
            className={`grid-item-single circle${playerNum}`}
            style={{ opacity: isDragging ? 0.2 : 1, gridRowStart: 1 }}
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

      <Grid boardState={boardState}></Grid>
    </div>
  );
};

export default Board;
