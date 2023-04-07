import React, { FC, Fragment, useState } from "react";
import api, { NewRoom } from "./api";

const createOpponentsLink = (room: NewRoom) => {
  return `${window.location.href}game/${room.id}/${room.game.playerTwo.id}`;
};

const createOwnLink = (room: NewRoom) => {
  return `${window.location.href}game/${room.id}/${room.game.playerOne.id}`;
};

const CreateGame: FC = () => {
  const [newRoom, setNewRoom] = useState<NewRoom | null>(null);

  const postCreateGame = async () => {
    console.log("perform post request to create game");
    const response = await api.createRoom();
    if (response.status === 200) {
      setNewRoom(response.data);
      console.log(response.data);
    } else {
      console.warn("could not create new room", response);
    }
  };

  return (
    <Fragment>
      <div style={{ marginBottom: 16 }}> 4 Wins </div>
      <button onClick={postCreateGame}> create new game </button>

      {newRoom !== null && (
        <div>
          <h3> Game created </h3>
          <div> Send this link to your opponent: </div>
          <div style={{ marginBottom: 12 }}>
            <a href={createOpponentsLink(newRoom)}>
              {createOpponentsLink(newRoom)}
            </a>
          </div>

          <div>
            <a href={createOwnLink(newRoom)}> Enter the game ...</a>
          </div>
        </div>
      )}
    </Fragment>
  );
};

export default CreateGame;
