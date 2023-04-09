import { useEffect } from "react";
import { Client, IMessage, StompSubscription } from "@stomp/stompjs";

const useSubscription = <T>(
  client: Client | null,
  topic: string | null,
  callback: (res: T) => void
) => {
  useEffect(() => {
    let subscription: StompSubscription;

    const callbackWrapper = (res: IMessage) => {
      const parsed = JSON.parse(res.body) as T;
      callback(parsed);
    };

    if (client && topic) {
      console.log(`subscribe to ${topic}`);
      subscription = client.subscribe(topic, callbackWrapper);
    }
    const cleanUp = () => {
      if (subscription) {
        console.log(`unsubscribe from ${topic}`);
        subscription.unsubscribe();
      }
    };
    return cleanUp;
  }, [client, topic, callback]);
};

export default useSubscription;
