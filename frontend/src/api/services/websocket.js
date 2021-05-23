import authHeader, { BASE_URL } from "../http";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

let stompClient = null;

export default {
  connect(subscribe) {
    const socket = new SockJS(BASE_URL + "/websocket");
    stompClient = Stomp.over(socket);
    let autHead = authHeader();
    stompClient.connect({ autHead }, function (frame) {
      console.log("Connected: " + frame);
      if (subscribe != null) {
        console.log(subscribe);
        stompClient.subscribe(subscribe, function (message) {
          alert(JSON.parse(message.body).message);
        });
      }
    });
  },
  sendMessage(messageBody, endpoint) {
    let autHead = authHeader();
    stompClient.send(endpoint, messageBody, { autHead });
  },
  disconnect() {
    if (stompClient !== null) {
      stompClient.disconnect();
    }
    console.log("Disconnected");
  },
};
