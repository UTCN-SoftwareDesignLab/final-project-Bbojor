<template>
  <v-card>
    <v-toolbar color="primary" dark>
      {{ "Hi there, " + username + "!" }}
    </v-toolbar>
    <v-form>
      <v-text-field v-model="user.email" label="Email" />
      <v-text-field v-model="user.password" label="Password" />
    </v-form>
    <input type="file" accept="image/*" @change="onFilePicked" />
    <v-card-actions>
      <v-btn @click="persist"> Save Changes</v-btn>
      <v-btn class="btn btn-info" @click="changeProfilePicture"
        >Change profile picture</v-btn
      >
      <v-btn @click="changePassword">Change Password</v-btn>
      <v-btn @click="deleteAccount">Delete Account</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import api from "@/api";
import router from "@/router";

export default {
  name: "ProfileView",
  data() {
    return {
      user: {},
      image: null,
    };
  },
  methods: {
    persist() {
      if (!this.user.email) this.user.email = this.email;
      api.users
        .edit({
          id: this.userId,
          username: this.username,
          email: this.user.email,
        })
        .then(() => {
          this.$store.commit("auth/editedUserEmail", this.user.email);
          this.$emit("refresh");
        })
        .catch((error) => {
          alert(error.response.data);
        });
    },
    changePassword() {
      api.users
        .changePassword(this.userId, {
          password: this.user.password,
        })
        .then(() => this.$emit("refresh"))
        .catch((error) => {
          alert(error.response.data);
        });
    },
    deleteAccount() {
      if (confirm("You will lose everything")) {
        api.users
          .delete(this.userId)
          .then(() => {
            this.$store.dispatch("auth/logout");
            router.push("/");
          })
          .catch((error) => {
            alert(error.response.data);
          });
      }
    },
    onFilePicked(event) {
      this.image = event.target.files[0];
    },
    changeProfilePicture() {
      const formData = new FormData();
      formData.append("file", this.image);
      api.media
        .create(formData)
        .then((response) => {
          api.users
            .edit({
              id: this.userId,
              username: this.username,
              email: this.email,
              avatarId: response.id,
            })
            .then(() => {
              this.$store.commit(
                "auth/editedUserAvatar",
                String(response.fileName)
              );
            })
            .catch((error) => {
              console.log(error);
              alert(error.response.data);
            });
        })
        .catch((error) => {
          console.log(error);
          alert(error.response.data);
        });
    },
  },
  computed: {
    userId() {
      return this.$store.getters["auth/userId"];
    },
    username() {
      return this.$store.getters["auth/username"];
    },
    email() {
      return this.$store.getters["auth/email"];
    },
    avatar() {
      return require("@/../../media/" + this.$store.getters["auth/avatar"]);
    },
  },
};
</script>

<style scoped></style>
