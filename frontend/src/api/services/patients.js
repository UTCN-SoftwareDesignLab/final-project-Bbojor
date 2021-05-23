import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPatients() {
    return HTTP.get(BASE_URL + "/patients", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allPatientsFiltered(search) {
    return HTTP.get(BASE_URL + "/patients/filtered?" + search, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(patient) {
    console.log(patient);
    return HTTP.post(BASE_URL + "/patients", patient, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(patient) {
    return HTTP.put(BASE_URL + "/patients/" + patient.id, patient, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/patients/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
