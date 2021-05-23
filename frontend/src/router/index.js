import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import Home from "../views/Home";
import ProfileView from "@/views/ProfileView";
import PatientList from "@/views/PatientList";
import ConsultationList from "@/views/ConsultationList";

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
    path: "/patients",
    name: "Patients",
    component: PatientList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },
  {
    path: "/consultations",
    name: "Consultations",
    component: ConsultationList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
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
];

const router = new VueRouter({
  routes,
});

export default router;
