import api from "../api";

const user = JSON.parse(localStorage.getItem("user"));
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login({ commit }, user) {
      return api.auth.login(user).then(
        (user) => {
          commit("loginSuccess", user);
          return Promise.resolve(user);
        },
        (error) => {
          commit("loginFailure");
          return Promise.reject(error);
        }
      );
    },
    logout({ commit }) {
      api.auth.logout();
      commit("logout");
    },
    register({ commit }, user) {
      return api.auth.register(user).then(
        (response) => {
          commit("registerSuccess");
          return Promise.resolve(response.data);
        },
        (error) => {
          commit("registerFailure");
          return Promise.reject(error);
        }
      );
    },
    editUser({ commit }, user) {
      console.log(user);
      api.users.edit({
        id: user.id,
        username: user.username,
        email: user.email,
        roles: user.roles,
        avatarId: user.avatarId,
      });
      commit("editUser", { email: user.email, avatar: user.avatarFile });
    },
  },
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
    },
    editUser(state, data) {
      state.user.email = data.email;
      state.user.avatar = data.avatar;
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
    registerFailure(state) {
      state.status.loggedIn = false;
    },
  },
  getters: {
    isAdmin: (state) => {
      return state.user.roles.includes("ADMIN");
    },
    isMod: (state) => {
      return state.user.roles.includes("MODERATOR");
    },
    roles: (state) => {
      return state.user.roles;
    },
    userId: (state) => {
      return state.user.id;
    },
    username: (state) => {
      return state.user.username;
    },
    avatar: (state) => {
      return state.user.avatar;
    },
    email: (state) => {
      return state.user.email;
    },
  },
};
