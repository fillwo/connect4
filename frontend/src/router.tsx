import { createHashRouter } from "react-router-dom";

import Play from "./routes/Play/Play";
import CreateGame from "./routes/Root/CreateGame";
import Game from "./routes/Game/Game";

const router = createHashRouter([
  {
    path: "/",
    element: <CreateGame></CreateGame>,
  },
  {
    path: "/play/:roomId/:playerId",
    element: <Play></Play>,
  },
  {
    path: "/game/:roomId/:playerIdOne/:playerIdTwo",
    element: <Game></Game>,
  },
]);

export default router;
