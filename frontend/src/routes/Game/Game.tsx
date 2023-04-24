import React, { FC } from "react";
import { useParams, Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

const createPlayLink = (roomId: string, playerId: string, full = false) => {
  if (full) {
    return `${window.location.origin}/#/play/${roomId}/${playerId}`;
  }
  return `/play/${roomId}/${playerId}`;
};

const Game: FC = () => {
  const { roomId, playerIdOne, playerIdTwo } = useParams();
  const navigate = useNavigate();

  return (
    <div className="center-center">
      <div style={{ textAlign: "center" }}>
        <h1> Game Created! </h1>
        <h2> Share this Link with a Friend: </h2>

        {roomId && playerIdOne && playerIdTwo && (
          <div>
            <div style={{ marginBottom: 40 }}>
              <Link className="link" to={createPlayLink(roomId, playerIdTwo)}>
                {createPlayLink(roomId, playerIdTwo, true)}
              </Link>
            </div>

            <div>
              <button
                onClick={() => navigate(createPlayLink(roomId, playerIdOne))}
              >
                Enter the game ...
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Game;
