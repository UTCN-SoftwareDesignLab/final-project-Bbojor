<template>
  <v-card class="ma-1" color="grey lighten-4" flat tile>
    <v-toolbar height="200">
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
    switchRoute() {
      console.log(router.currentRoute.name);
      if (router.currentRoute.name === "Consultations")
        router.push("/patients");
      else router.push("/consultations");
      this.$forceUpdate();
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
