<template>
  <v-card class="ma-1" color="grey lighten-4" flat tile>
    <v-toolbar height="200">
      <v-btn @click="toHome">Home</v-btn>
      <v-btn v-if="isAdmin" @click="toUsers">Users</v-btn>
      <v-spacer></v-spacer>
      <UserCard></UserCard>
    </v-toolbar>
    <v-divider vertical></v-divider>

  </v-card>
</template>

<script>
import router from "../router";
import UserCard from "@/components/UserCard";

export default {
  name: "TopBar",
  components: { UserCard },
  methods: {
    toHome() {
      router.push("/home");
    },
    toUsers() {
      router.push("/users");
    },
  },
  computed: {
    isAdmin() {
      return this.$store.getters["auth/isAdmin"];
    },
    isMod() {
      return this.$store.getters["auth/isMod"];
    },
  },
};
</script>

<style scoped></style>
