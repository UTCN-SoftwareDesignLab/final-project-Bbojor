<template>
  <v-app>
    <v-main>
      <v-container fluid>
        <TopBar v-if="isLoggedIn"></TopBar>
        <v-container fluid>
          <router-view class="float-left"></router-view>
          <ChatWindow
            fixed
            v-if="isLoggedIn"
            :visible="showChatWindow"
            @showChat="showChat"
          ></ChatWindow>
          <v-btn
            v-if="isLoggedIn"
            fixed
            class="v-btn--bottom v-btn--right"
            @click="toggleChat"
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
    toggleChat() {
      this.showChatWindow = !this.showChatWindow;
    },
    showChat() {
      this.showChatWindow = true;
    },
  },
  computed: {
    isLoggedIn: function () {
      return this.$store.state.auth.status.loggedIn;
    },
  },
};
</script>
