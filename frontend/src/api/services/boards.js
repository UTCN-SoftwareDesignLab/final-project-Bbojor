import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBoards() {
    return HTTP.get(BASE_URL + "/boards", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getBoard(id) {
    return HTTP.get(BASE_URL + "/boards/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(board) {
    return HTTP.post(BASE_URL + "/boards", board, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(board) {
    return HTTP.put(BASE_URL + "/boards/" + board.id, board, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/boards/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
