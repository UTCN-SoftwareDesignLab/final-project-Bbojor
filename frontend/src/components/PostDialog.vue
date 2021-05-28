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

      this.formData.append(
        "post",
        new Blob(
          [
            JSON.stringify({
              text: this.post.text,
              threadId: this.threadId,
              userId: this.userId,
            }),
          ],
          {
            type: "application/json",
          }
        )
      );

      api.posts
        .create(this.formData)
        .then(() => {
          this.$emit("refresh");
        })
        .catch((error) => {
          alert(JSON.stringify(error.response.data));
        });
    },
    close() {
      this.post = {};
      this.formData = null;
      this.files = [];
      document.getElementById("fileInput").value = "";
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
