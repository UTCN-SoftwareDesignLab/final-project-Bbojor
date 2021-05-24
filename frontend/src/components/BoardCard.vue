<template>
  <v-card class="ma-6" max-width="344" outlined>
    <v-list-item three-line>
      <v-list-item-content>
        <v-list-item-title
          class="headline mb-1"
          v-html="board.name"
        ></v-list-item-title>
        <v-list-item-subtitle v-html="board.description"></v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-card-actions>
      <v-btn text @click="goToBoard">View</v-btn>
      <v-btn v-if="isAdmin" text @click="editBoard">Edit Board</v-btn>
      <v-btn v-if="isAdmin" text @click="deleteBoard">Delete Board</v-btn>
    </v-card-actions>

    <BoardDialog
      :opened="dialogVisible"
      :board="board"
      @close-dialog="closeDialog"
    ></BoardDialog>
  </v-card>
</template>

<script>
import BoardDialog from "@/components/BoardDialog";
import router from "@/router";
import api from "@/api";

export default {
  name: "BoardCard",
  components: { BoardDialog },
  data() {
    return {
      dialogVisible: false,
    };
  },
  props: {
    board: {},
  },
  methods: {
    editBoard() {
      this.dialogVisible = true;
    },
    deleteBoard() {
      if (confirm("Are you sure you want to delete this board?")) {
        api.boards
          .delete(this.board.id)
          .then(() => {
            this.$emit("refresh");
          })
          .catch((error) => {
            alert(error.response.data);
          });
      }
    },
    async refresh() {
      this.board = await api.boards.getBoard(this.board.id);
    },
    closeDialog() {
      this.dialogVisible = false;
      this.refresh();
    },
    goToBoard() {
      router.push({
        name: "Threads",
        params: { board: this.board },
      });
    },
  },
  computed: {
    isAdmin() {
      return this.$store.getters["auth/isAdmin"];
    },
  },
};
</script>

<style scoped></style>
