<template>
  <v-app>
    <v-main>
      <v-container fluid>
        <TopBar v-if="isLoggedIn"></TopBar>
        <v-container fluid>
          <router-view class="float-left"></router-view>
          <ChatWindow v-if="isLoggedIn" :visible="showChatWindow"  class="float-right"></ChatWindow>
          <v-btn
            v-if="isLoggedIn"
            fixed
            class="v-btn--bottom v-btn--right"
            @click="showChat"
            >Chat</v-btn
          >
        </v-container>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import TopBar from "./components/TopBar";
import ChatWindow from "@/components/ChatWindow";
export default {
  name: "App",
  components: { TopBar, ChatWindow },
  data: () => ({
    showChatWindow: false,
  }),
  methods: {
    showChat() {
      console.log("here");
      this.showChatWindow = !this.showChatWindow;
    },
  },
  computed: {
    isLoggedIn: function () {
      return this.$store.state.auth.status.loggedIn;
    },
  },
};
</script>
