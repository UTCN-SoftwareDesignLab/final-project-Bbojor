<template>
  <v-card>
    <v-card-title>
      {{ this.board.name }}
      <v-spacer></v-spacer>
      <v-btn @click="createThread">Add Thread</v-btn>
    </v-card-title>
    <ThreadCard
      v-for="thread in threads"
      v-bind:thread="thread"
      v-bind:key="thread.id"
      @refresh="refreshList"
    ></ThreadCard>
    <ThreadDialog
      :opened="dialogVisible"
      :boardId="board.id"
      @refresh="refreshList"
      @close-dialog="closeDialog"
    ></ThreadDialog>
  </v-card>
</template>

<script>
// @ is an alias to /src

import ThreadDialog from "@/components/ThreadDialog";
import ThreadCard from "@/components/ThreadCard";
import api from "@/api";

export default {
  name: "Threads",
  components: { ThreadDialog, ThreadCard },
  data() {
    return {
      board: {},
      threads: [],
      search: "",
      dialogVisible: false,
    };
  },
  methods: {
    createThread() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedThread = {};
      this.boards = await api.boards.threads.allThreadsFiltered("boardId=" + this.board.id);;
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
    this.threads = await api.threads.allThreadsFiltered("boardId=" + this.board.id);
  },
};
</script>
