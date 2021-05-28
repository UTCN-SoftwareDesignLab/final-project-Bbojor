import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allThreadsFiltered(search) {
    return HTTP.get(BASE_URL + "/threads/filtered?" + search, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getThread(id) {
    return HTTP.get(BASE_URL + "/threads/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(thread) {
    let headers = authHeader();
    headers["Content-Type"] = undefined;
    console.log(headers);
    return HTTP.post(BASE_URL + "/threads", thread, {
      headers: headers,
    }).then((response) => {
      return response.data;
    });
  },
  edit(thread) {
    return HTTP.put(BASE_URL + "/threads/" + thread.id, thread, {
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
