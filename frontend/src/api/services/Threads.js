import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBoards() {
    return HTTP.get(BASE_URL + "/threads", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(board) {
    return HTTP.post(BASE_URL + "/threads", board, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(board) {
    return HTTP.put(BASE_URL + "/threads/" + board.id, board, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/threads/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
