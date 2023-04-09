const BASE_URL =
  process.env.NODE_ENV === "development" ? "http://localhost:8080" : "/";

const SOCKJS_ENDPOINT =
  process.env.NODE_ENV === "development" ? "http://localhost:8080/ws" : "/ws";

export { BASE_URL, SOCKJS_ENDPOINT };
