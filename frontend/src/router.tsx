import { createHashRouter } from "react-router-dom";

import Game from "./Game";
import CreateGame from "./CreateGame";

const router = createHashRouter([
  {
    path: "/",
    element: <CreateGame></CreateGame>,
  },
  {
    path: "/game/:roomId/:playerId",
    element: <Game></Game>,
  },
]);

export default router;
