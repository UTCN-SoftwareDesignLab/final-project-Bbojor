<template>
  <v-card>
    <v-card-title>
      Boards
      <v-spacer></v-spacer>
      <v-btn v-if="isAdmin" @click="addBoard">Add Board</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="boards"
      :search="search"
      @click:row="editBoard"
    ></v-data-table>
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
import api from "@/api";

export default {
  name: "Home",
  components: { BoardDialog },
  data() {
    return {
      boards: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          value: "name",
        },
        { text: "Description", value: "description" },
      ],
      dialogVisible: false,
      selectedBoard: {},
    };
  },
  methods: {
    editBoard(board) {
      this.selectedBoard = board;
      this.dialogVisible = true;
    },
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
