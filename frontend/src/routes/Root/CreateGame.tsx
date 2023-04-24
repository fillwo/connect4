import React, { FC } from "react";
import api from "../../api";
import { useNavigate } from "react-router-dom";

const CreateGame: FC = () => {
  const navigate = useNavigate();

  const postCreateGame = async () => {
    console.log("perform post request to create game");
    const response = await api.createRoom();
    if (response.status === 200) {
      console.log(response.data);
      console.log("calling redirect");
      navigate(
        `game/${response.data.id}/${response.data.game.playerOne.id}/${response.data.game.playerTwo.id}`
      );
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
      </div>
    </div>
  );
};

export default CreateGame;
