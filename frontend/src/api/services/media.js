import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  findById(id) {
    return HTTP.get(BASE_URL + "/media/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(image) {
    return HTTP.post(BASE_URL + "/media", image, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/media/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
