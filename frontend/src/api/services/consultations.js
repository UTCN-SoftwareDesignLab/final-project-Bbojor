import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allConsultations() {
    return HTTP.get(BASE_URL + "/consultations", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allConsultationsFiltered(search) {
    return HTTP.get(BASE_URL + "/consultations/filtered?" + search, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(consultation) {
    return HTTP.post(BASE_URL + "/consultations", consultation, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(consultation) {
    return HTTP.put(BASE_URL + "/consultations/" + consultation.id, consultation, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/consultations/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
