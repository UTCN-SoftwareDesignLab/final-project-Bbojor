<template>
  <v-card>
    <v-card-title>
      {{ this.thread.title }}
      <v-spacer></v-spacer>
      <v-btn @click="createThread">Add Thread</v-btn>
    </v-card-title>
    <PostCard
      v-for="post in posts"
      v-bind:post="post"
      v-bind:key="post.id"
      @refresh="refreshList"
    ></PostCard>
    <PostDialog
      :opened="dialogVisible"
      @refresh="refreshList"
      @close-dialog="closeDialog"
    ></PostDialog>
  </v-card>
</template>

<script>
// @ is an alias to /src

import PostDialog from "@/components/PostDialog";
import PostCard from "@/components/PostCard";
import api from "@/api";

export default {
  name: "Threads",
  components: { PostDialog, PostCard },
  data() {
    return {
      thread: {},
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
      this.posts = await api.posts.allBoards();
    },
    closeDialog() {
      this.dialogVisible = false;
      this.refreshList();
    },
  },
  computed: {
    isModerator() {
      return this.$store.getters["auth/isAdmin"];
    },
  },
  async created() {
    this.board = this.$route.params.board;
    this.threads = await api.threads.allThreadsFiltered(
      "boardId=" + this.board.id
    );
  },
};
</script>
