<template>
  <v-card>
    <v-card-title>
      Boards
      <v-spacer></v-spacer>
      <v-btn v-if="isAdmin" @click="addBoard">Add Board</v-btn>
    </v-card-title>
    <BoardCard
      v-for="board in boards"
      v-bind:board="board"
      v-bind:key="board.id"
      @refresh="refreshList"
    ></BoardCard>
    <BoardDialog
      :opened="dialogVisible"
      :board="selectedBoard"
      @refresh="refreshList"
      @close-dialog="closeDialog"
    ></BoardDialog>
  </v-card>
</template>

<script>
// @ is an alias to /src

import BoardDialog from "@/components/BoardDialog";
import BoardCard from "@/components/BoardCard";
import api from "@/api";

export default {
  name: "Home",
  components: { BoardDialog, BoardCard },
  data() {
    return {
      boards: [],
      dialogVisible: false,
      selectedBoard: {},
    };
  },
  methods: {
    addBoard() {
      this.selectedBoard = {};
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBoard = {};
      this.boards = await api.boards.allBoards();
    },
    closeDialog() {
      this.dialogVisible = false;
      this.refreshList();
    },
  },
  computed: {
    isAdmin() {
      return this.$store.getters["auth/isAdmin"];
    },
  },
  async created() {
    this.boards = await api.boards.allBoards();
  },
};
</script>
