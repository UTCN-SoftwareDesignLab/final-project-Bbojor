import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPostsFiltered(search) {
    return HTTP.get(BASE_URL + "/posts/filtered?" + search, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getPost(id) {
    return HTTP.get(BASE_URL + "/posts/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(post) {
    return HTTP.post(BASE_URL + "/posts", post, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(post) {
    return HTTP.put(BASE_URL + "/posts/" + post.id, post, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/posts/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
