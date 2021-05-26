<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="610"
    :value="opened"
    :persistent="true"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Create post </v-toolbar>
        <v-form>
          <v-textarea v-model="post.text"> Description </v-textarea>
          <input
            type="file"
            multiple="multiple"
            accept="image/*"
            @change="onFilesPicked"
          />
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
      formData: null,
      files: [],
    };
  },
  methods: {
    persist() {
      if (this.formData == null) this.formData = new FormData();
      const json = JSON.stringify({
        text: this.post.text,
        threadId: this.threadId,
        userId: this.userId,
      });
      this.formData.set("post", json);
      api.posts
        .create(this.formData)
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
    onFilesPicked(event) {
      this.formData = null;
      this.formData = new FormData();
      for (let i = 0; i < event.target.files.length; i++) {
        this.formData.append("files", event.target.files[i]);
      }
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
