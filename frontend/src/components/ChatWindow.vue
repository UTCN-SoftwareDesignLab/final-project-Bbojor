<template>
  <v-card v-if="visible" width="600">
    <v-col>
      <v-container v-if="currentChatPartner === null">
        <v-text-field
          label="Username"
          single-line
          v-model="searchedName"
          hide-details
        ></v-text-field>
        <v-btn @click="searchUsers" icon> <v-icon>mdi-magnify </v-icon></v-btn>

        <v-btn
          v-for="user in users"
          v-bind:key="user.id"
          class="ma-3"
          @click="startChat(user)"
        >
          {{ user.username }}</v-btn
        >
      </v-container>
    </v-col>
    <v-col :cols="10">
      <v-container v-if="currentChatPartner != null">
        <v-list-item-title
          class="headline mb-1"
          v-html="this.currentChatPartner.username"
        ></v-list-item-title>

        <v-list
          :rounded="true"
          style="max-height: 400px"
          class="overflow-y-auto"
        >
          <v-list-item v-for="message in messages" v-bind:key="message.id">
            <v-list-item-avatar v-if="message.senderId === userId">
              <v-img :src="getFile(avatar)" width="50" height="50"></v-img>
            </v-list-item-avatar>

            <v-list-item-title
              v-if="message.senderId === userId"
              v-html="message.message"
            ></v-list-item-title>

            <v-list-item-title
              v-if="message.senderId !== userId"
              v-html="message.message"
              class="d-flex justify-end"
            ></v-list-item-title>

            <v-list-item-avatar v-if="message.senderId !== userId">
              <v-img
                :src="getFile(currentChatPartner.avatarFile)"
                width="50"
                height="50"
              ></v-img>
            </v-list-item-avatar>
          </v-list-item>
        </v-list>

        <v-text-field
          label="Message"
          single-line
          v-model="message"
          hide-details
          class="ma-4"
        ></v-text-field>
        <v-btn @click="messageUser" icon>
          <v-icon> mdi-arrow-left-box </v-icon>
        </v-btn>
        <v-btn @click="disconnect"> Disconnect </v-btn>
      </v-container>
    </v-col>
  </v-card>
</template>

<script>
import api from "@/api";
export default {
  name: "ChatWindow",
  props: {
    visible: Boolean,
  },
  data() {
    return {
      users: [],
      messages: [],
      message: null,
      currentChatPartner: null,
      searchedName: "",
    };
  },
  methods: {
    async searchUsers() {
      this.users = await api.users.allUsersByUsername(this.searchedName);
    },
    startChat(user) {
      api.chat.subscribe(
        "/user/" + this.userId + "/" + user.id,
        this.addMessage.bind(this)
      );
      this.loadChat(user.id);
    },
    addMessage(message) {
      console.log(message);
      this.messages.push(message);
    },
    alertStartChat(message) {
      if (
        this.currentChatPartner === null ||
        this.currentChatPartner.id !== message.senderId
      )
        if (confirm(message.message)) {
          this.loadChat(message.senderId);
          api.chat.subscribe(
            "/user/" + this.userId + "/" + message.senderId,
            this.addMessage
          );
        }
    },
    messageUser() {
      if (this.message != null) {
        const jsonNotification = JSON.stringify({
          message: "User " + this.username + " sent you a message!",
          senderId: this.userId,
          recipientId: this.currentChatPartner.id,
        });

        api.chat.sendMessage(jsonNotification, "/api/chat/requests");

        const jsonMessage = JSON.stringify({
          message: this.message,
          senderId: this.userId,
          recipientId: this.currentChatPartner.id,
        });
        api.chat.sendMessage(jsonMessage, "/api/chat/messages");

        this.messages.push({
          message: this.message,
          senderId: this.userId,
          recipientId: this.currentChatPartner.id,
        });

        this.message = null;
      }
    },
    disconnect() {
      api.chat.unsub("/user/" + this.userId + "/" + this.currentChatPartner.id);
      this.currentChatPartner = null;
    },
    async loadChat(userId) {
      this.currentChatPartner = await api.users.getUser(userId);
      this.messages = await api.chat.chatFiltered(this.userId, userId);
    },
    getFile(fileName) {
      return require("@/../../media/" + fileName);
    },
  },
  created() {
    api.chat.disconnect();
    api.chat.connect("/user/" + this.userId + "/requests", this.alertStartChat);
  },
  computed: {
    userId() {
      return this.$store.getters["auth/userId"];
    },
    username() {
      return this.$store.getters["auth/username"];
    },
    avatar() {
      return this.$store.getters["auth/avatar"];
    },
  },
};
</script>

<style scoped></style>
