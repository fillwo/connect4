import React, { FC } from "react";
import { XYCoord, useDrag, useDragLayer, useDrop } from "react-dnd";

import styles from "./Board.module.css";

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
    <div className={styles.gridContainer}>
      {boardState
        .reduce((acc, curr) => {
          return [...acc, ...curr];
        }, [])
        .map((chip, idx) => {
          return (
            <div
              className={`${styles.gridItem} ${styles[`circle${chip}`]}`}
              key={idx}
            ></div>
          );
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
      className={`${styles.gridItemDrop} ${
        isOver ? styles[`circle${playerNum}DropArea`] : ""
      }`}
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
        className={`${styles.gridItemSingle} ${styles[`circle${playerNum}`]}`}
        style={{ display: "none", pointerEvents: "none", gridColumnStart: 1 }}
      ></div>
    );

  return (
    <div
      className={`${styles.gridItemSingle} ${styles[`circle${playerNum}`]}`}
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
        <div className={styles.gridContainerSingle}>
          {isTouch && <CustomDragLayer playerNum={playerNum}></CustomDragLayer>}
          <div
            ref={drag}
            className={`${styles.gridItemSingle} ${
              styles[`circle${playerNum}`]
            }`}
            style={{ opacity: isDragging ? 0.2 : 1, gridRowStart: 1 }}
          ></div>
          <div
            className={styles.dragMeItem}
            style={{ opacity: isDragging ? 0.2 : 1 }}
          >
            <div className={styles.dragMeText}>drag me</div>
          </div>
        </div>
        <div
          className={styles.gridContainerDrop}
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
