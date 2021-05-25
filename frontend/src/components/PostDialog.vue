<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="610"
    :value="opened"
    :persistent="true"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Create thread </v-toolbar>
        <v-form>
          <v-textarea v-model="post.text"> Description </v-textarea>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Post </v-btn>
          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "PostDialog",
  props: {
    opened: Boolean,
    threadId: Number,
  },
  data() {
    return {
      post: {},
    };
  },
  methods: {
    persist() {
      api.posts
        .create({
          text: this.post.text,
          threadId: this.threadId,
          userId: this.userId,
        })
        .then(() => {
          this.$emit("refresh");
        })
        .catch((error) => {
          alert(error.response.data);
        });
    },
    close() {
      this.$emit("close-dialog");
    },
  },
  computed: {
    userId: function () {
      return this.$store.getters["auth/userId"];
    },
  },
};
</script>

<style scoped></style>
