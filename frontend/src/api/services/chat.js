import authHeader, { BASE_URL, HTTP } from "../http";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

let stompClient = null;
let sub_dict = {};

export default {
  chatFiltered(senderId, recipientId) {
    return HTTP.get(
      BASE_URL +
        "/messages/filtered?senderId=" +
        senderId +
        "&recipientId=" +
        recipientId,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  connect(subscribe, callback) {
    const socket = new SockJS(BASE_URL + "/websocket");
    stompClient = Stomp.over(socket);
    let autHead = authHeader();
    stompClient.connect({ autHead }, function (frame) {
      console.log("Connected: " + frame);
      if (subscribe != null) {
        sub_dict[subscribe] = stompClient.subscribe(
          subscribe,
          function (message) {
            callback(JSON.parse(message.body));
          }
        );
      }
    });
  },
  subscribe(endpoint, callback) {
    sub_dict[endpoint] = stompClient.subscribe(endpoint, function (message) {
      callback(JSON.parse(message.body));
    });
  },
  unsub(endpoint) {
    sub_dict[endpoint].unsubscribe();
  },
  sendMessage(messageBody, endpoint) {
    let autHead = authHeader();
    stompClient.send(endpoint, messageBody, { autHead });
  },
  disconnect() {
    if (stompClient !== null) {
      for (const endpoint in sub_dict) {
        sub_dict[endpoint].unsubscribe();
      }
      stompClient.disconnect();
    }
    console.log("Disconnected");
  },
};
