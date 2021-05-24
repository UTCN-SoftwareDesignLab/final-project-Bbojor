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
          <v-text-field v-model="thread.title" label="Name" />
          <v-textarea v-model="thread.text"> Description </v-textarea>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Create </v-btn>
          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ThreadDialog",
  props: {
    opened: Boolean,
    boardId: Number,
  },
  data() {
    return {
      thread: {},
    };
  },
  methods: {
    persist() {
      api.threads
        .create({
          title: this.thread.title,
          text: this.thread.text,
          boardId: this.boardId,
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
