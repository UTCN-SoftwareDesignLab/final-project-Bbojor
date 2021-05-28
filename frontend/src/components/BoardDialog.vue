<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="610"
    :value="opened"
    :persistent="true"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create board" : "Edit board" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="board.name" label="Name" />
          <v-textarea v-model="board.description"> Description </v-textarea>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist" >
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteBoard">Delete</v-btn>
          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BoardDialog",
  props: {
    board: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.boards
          .create({
            id: this.board.id,
            name: this.board.name,
            description: this.board.description,
          })
          .then(() => {
            this.$emit("refresh");
          })
          .catch((error) => {
            alert(error.response.data);
          });
      } else {
        api.boards
          .edit({
            id: this.board.id,
            name: this.board.name,
            description: this.board.description,
          })
          .then(() => this.$emit("close-dialog"))
          .catch((error) => {
            alert(JSON.stringify(error.response.data));
          });
      }
    },
    deleteBoard() {
      api.boards.delete(this.board.id).then(() => this.$emit("refresh"));
    },
    close() {
      this.$emit("close-dialog");
    },
  },
  computed: {
    isNew: function () {
      return !this.board.id;
    },
  },
};
</script>

<style scoped></style>
