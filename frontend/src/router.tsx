import { createHashRouter } from "react-router-dom";

import Play from "./routes/Play/Play";
import CreateGame from "./routes/Root/CreateGame";

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
