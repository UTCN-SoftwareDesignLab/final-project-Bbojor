import authHeader, { BASE_URL, HTTP } from "../http";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

let stompClient = null;

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
  chatPartners(id) {
    return HTTP.get(BASE_URL + "/messages/partners/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
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
