import { createBrowserRouter } from "react-router-dom";

import App from "./App";
import Game from "./Game";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App></App>,
  },
  {
    path: "/game/:roomId/:playerId",
    element: <Game></Game>,
  },
]);

export default router;
