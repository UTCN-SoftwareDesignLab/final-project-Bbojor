<template>
  <v-container class="float-left">
    <v-container class="float-left elevation-5 ma-2">
      <v-row class="elevation-2">
        <div class="title mb-5">{{ this.thread.title }}</div>
      </v-row>
      <v-row>
        <v-col class="elevation-2">
          <div class="title mb-2">{{ this.user.username }}</div>
          <v-img
            max-width="100"
            max-height="100"
            class="mb-3"
            :src="avatar()"
          />
          <v-btn v-if="isDifferentUser" @click="startChat"> Chat </v-btn>
        </v-col>
        <v-col :cols="10">
          <v-list-item-subtitle>{{ this.thread.text }}</v-list-item-subtitle>
        </v-col>
      </v-row>
    </v-container>

    <v-btn fixed class="v-btn--right" @click="createPost">Post</v-btn>

    <v-container class="ma-2 float-left">
      <PostCard
        v-for="post in posts"
        v-bind:post="post"
        v-bind:key="post.id"
        @refresh="refreshList"
      ></PostCard>
    </v-container>

    <PostDialog
      :opened="dialogVisible"
      :threadId="thread.id"
      @refresh="refreshList"
      @close-dialog="closeDialog"
    ></PostDialog>
  </v-container>
</template>

<script>
// @ is an alias to /src

import PostDialog from "@/components/PostDialog";
import PostCard from "@/components/PostCard";
import api from "@/api";

export default {
  name: "Posts",
  components: { PostDialog, PostCard },
  data() {
    return {
      thread: {},
      user: {},
      posts: [],
      search: "",
      dialogVisible: false,
    };
  },
  methods: {
    createPost() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedThread = {};
      this.posts = await api.posts.allPostsFiltered(
        "threadId=" + this.thread.id
      );
    },
    closeDialog() {
      this.dialogVisible = false;
      this.refreshList();
    },
    avatar() {
      return require("@/../../media/" + this.user.avatarFile);
    },
    startChat() {},
  },
  computed: {
    isModerator() {
      return this.$store.getters["auth/isMod"];
    },
    isDifferentUser() {
      return this.$store.getters["auth/userId"] !== this.user.id;
    },
  },
  async created() {
    this.thread = this.$route.params.thread;
    this.posts = await api.posts.allPostsFiltered("threadId=" + this.thread.id);
    this.user = await api.users.getUser(this.thread.userId);
  },
};
</script>
