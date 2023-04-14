import { useEffect, useState } from "react";

const useIsTouchScreen = () => {
  const [isTouch, setIsTouch] = useState(false);

  useEffect(() => {
    const check = "ontouchstart" in window;
    console.log("check touch screen", check);
    setIsTouch(check);
  }, []);

  return isTouch;
};

export default useIsTouchScreen;
