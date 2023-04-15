import React, { FC, useState } from "react";
import api, { NewRoom } from "./api";
import { Link } from "react-router-dom";

const createOpponentsLink = (room: NewRoom) => {
  return `play/${room.id}/${room.game.playerTwo.id}`;
};

const createOwnLink = (room: NewRoom) => {
  return `play/${room.id}/${room.game.playerOne.id}`;
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
    <div className="center-center">
      <div style={{ textAlign: "center" }}>
        <h1> Connect 4 </h1>
        <h3> Play a Game of Connect 4 with a Friend</h3>
        <button
          style={{ marginTop: 12, marginBottom: 80 }}
          onClick={postCreateGame}
        >
          NEW GAME
        </button>

        {newRoom !== null && (
          <div>
            <h3> Game created </h3>
            <div> Send this link to your opponent: </div>
            <div style={{ marginBottom: 12 }}>
              <Link to={createOpponentsLink(newRoom)}>
                {createOpponentsLink(newRoom)}
              </Link>
            </div>

            <div>
              <Link to={createOwnLink(newRoom)}> Enter the game ...</Link>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CreateGame;
