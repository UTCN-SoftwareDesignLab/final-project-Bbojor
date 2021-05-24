import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import Home from "../views/Home";
import ProfileView from "@/views/ProfileView";
import BoardView from "@/views/BoardView";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Items" });
      }
    },
  },
  {
    path: "/home",
    name: "Home",
    component: Home,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },
  {
    path: "/profile",
    name: "Profile",
    component: ProfileView,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },
  {
    path: "/threads",
    name: "Threads",
    component: BoardView,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },
];

const router = new VueRouter({
  routes,
});

export default router;
