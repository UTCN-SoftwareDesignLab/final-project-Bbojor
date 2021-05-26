<template>
  <v-container v-if="visible" >
    <v-col>
      <v-text-field
        label="Username"
        single-line
        v-model="searchedName"
        hide-details
      ></v-text-field>
      <v-btn @click="searchUsers" icon> <v-icon>mdi-magnify </v-icon></v-btn>

      <v-btn v-for="user in users" v-bind:key="user.id"  class="ma-3" @click="startChat(user.id)"> {{user.username}}</v-btn>

    </v-col>
    <v-col :cols="10">
    </v-col>
  </v-container>
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
      searchedName: String,
    };
  },
  methods: {
    async searchUsers() {
      this.users = await api.users.allUsersByUsername(this.searchedName);
    },
    startChat(id) {

    }
  },
};
</script>

<style scoped></style>
