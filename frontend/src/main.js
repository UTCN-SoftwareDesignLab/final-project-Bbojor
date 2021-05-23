import Vue from "vue";
import App from "./App.vue";
import moment from "moment";
import VueMask from "v-mask";
import router from "./router";
import vuetify from "./plugins/vuetify";
import "material-design-icons-iconfont/dist/material-design-icons.css";
import "./api";
import store from "./store";
import VueStomp from "vue-stomp";
import VuejsDialog from "vuejs-dialog"
Vue.use(VueStomp, "https://localhost:8080/notifications")

Vue.config.productionTip = false;
Vue.use(VueMask);
Vue.use(moment);
Vue.use(VuejsDialog);

new Vue({
  router,
  vuetify,
  store,
  render: (h) => h(App),
}).$mount("#app");
