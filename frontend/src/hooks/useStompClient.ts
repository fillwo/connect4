import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { SOCKJS_ENDPOINT } from "../config";

const useStompClient = () => {
  const [connected, setConnected] = useState(false);
  const [client, setClient] = useState<Client | null>(null);

  useEffect(() => {
    const stompClient = new Client({
      webSocketFactory() {
        return new SockJS(SOCKJS_ENDPOINT);
      },
      onConnect() {
        console.log("connected");
        setConnected(true);
        setClient(stompClient);
        console.log(stompClient);
      },
      onWebSocketClose() {
        console.log("disconnected");
        setConnected(false);
        setClient(null);
      },
    });

    console.log("activating stomp client");
    stompClient.activate();

    const cleanUp = () => {
      console.log("deactivating stomp client");
      stompClient.deactivate();
      setClient(null);
    };
    return cleanUp;
  }, []);

  return { connected, client };
};

export default useStompClient;
