<template>
  <v-container class="float-left elevation-5">
    <v-row>
      <v-col class="elevation-2">
        <div class="title mb-5">{{ this.user.username }}</div>
        <v-img max-width="100" max-height="100" class="mb-3" :src="avatar()" />
        <v-btn v-if="isMod || isPoster" text @click="deletePost"
          >Remove Post</v-btn
        >
      </v-col>
      <v-col :cols="10">
        <v-list-item-subtitle>{{ this.post.text }}</v-list-item-subtitle>

        <v-row class="flex-row float-left">
          <ToggleableImage
            v-for="image in this.post.media"
            class="ma-3 image"
            v-bind:key="image.id"
            :src="image.fileName"
          />
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import api from "@/api";
import ToggleableImage from "@/components/ToggleableImage";

export default {
  name: "PostCard",
  components: { ToggleableImage },
  data() {
    return {
      dialogVisible: false,
      user: {},
    };
  },
  props: {
    post: {},
  },
  methods: {
    deletePost() {
      if (confirm("Are you sure you want to delete this post?")) {
        api.posts
          .delete(this.post.id)
          .then(() => {
            this.$emit("refresh");
          })
          .catch((error) => {
            alert(error.response.data);
          });
      }
    },
    async refresh() {
      this.board = await api.posts.getPost(this.post.id);
    },
    closeDialog() {
      this.dialogVisible = false;
      this.refresh();
    },
    avatar() {
      return require("@/../../media/" + this.user.avatarFile);
    },
    startChat() {},
  },
  computed: {
    isMod() {
      return this.$store.getters["auth/isMod"];
    },
    isPoster() {
      return this.$store.getters["auth/userId"] === this.post.userId;
    },
  },
  async created() {
    console.log(this.post);
    this.user = await api.users.getUser(this.post.userId);
  },
};
</script>

<style scoped></style>
