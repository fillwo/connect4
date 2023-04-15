import { createHashRouter } from "react-router-dom";

import Play from "./Play";
import CreateGame from "./CreateGame";

const router = createHashRouter([
  {
    path: "/",
    element: <CreateGame></CreateGame>,
  },
  {
    path: "/play/:roomId/:playerId",
    element: <Play></Play>,
  },
]);

export default router;
