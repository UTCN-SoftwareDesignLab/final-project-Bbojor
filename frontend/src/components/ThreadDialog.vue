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
          <input
            type="file"
            multiple="multiple"
            id="fileInput"
            accept="image/*"
            @change="onFilesPicked"
          />
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
      formData: null,
      files: [],
    };
  },
  methods: {
    persist() {
      if (this.formData == null) this.formData = new FormData();

      this.formData.append(
        "thread",
        new Blob(
          [
            JSON.stringify({
              title: this.thread.title,
              text: this.thread.text,
              boardId: this.boardId,
              userId: this.userId,
            }),
          ],
          {
            type: "application/json",
          }
        )
      );

      api.threads
        .create(this.formData)
        .then(() => {
          this.$emit("refresh");
        })
        .catch((error) => {
          alert(JSON.stringify(error.response.data));
        });
    },
    onFilesPicked(event) {
      this.formData = null;
      this.formData = new FormData();
      for (let i = 0; i < event.target.files.length; i++) {
        this.formData.append("files", event.target.files[i]);
      }
    },
    close() {
      this.thread = {};
      this.formData = null;
      this.files = [];
      document.getElementById("fileInput").value = '';
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
