<template>
  <v-card class="ma-6" max-width="344" outlined>
    <v-list-item three-line>
      <v-list-item-content>
        <v-list-item-title
          class="headline mb-1"
          v-html="username"
        ></v-list-item-title>
      </v-list-item-content>

      <v-list-item-avatar tile size="80">
        <v-img :src="avatar()" />
      </v-list-item-avatar>
    </v-list-item>

    <v-card-actions>
      <v-btn text @click="viewProfile">View profile</v-btn>
      <v-btn text @click="logout">Logout</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import router from "@/router";

export default {
  name: "UserCard",
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
    viewProfile() {
      router.push("/profile");
    },
    avatar() {
      return require("@/../../media/" + this.$store.getters["auth/avatar"]);
    },
  },
  computed: {
    username() {
      return this.$store.getters["auth/username"];
    },
    userId() {
      return this.$store.getters["auth/userId"];
    },
  },
};
</script>

<style scoped></style>
