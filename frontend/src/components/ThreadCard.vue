<template>
  <v-card class="ma-6" max-width="344" outlined>
    <v-list-item three-line>
      <v-list-item-content>
        <v-list-item-title
          class="headline mb-1"
          v-html="thread.title"
        ></v-list-item-title>
      </v-list-item-content>

      <v-list-item-avatar v-if="hasMedia" tile size="80">
        <v-img :src="file()" />
      </v-list-item-avatar>
    </v-list-item>

    <v-card-actions>
      <v-btn text @click="goToThread">View</v-btn>
      <v-btn v-if="isMod" text @click="deleteThread">Remove Thread</v-btn>
    </v-card-actions>

    <ThreadDialog
      :opened="dialogVisible"
      :thread="thread"
      @refresh="refresh"
      @close-dialog="closeDialog"
    ></ThreadDialog>
  </v-card>
</template>

<script>
import ThreadDialog from "@/components/ThreadDialog";
import api from "@/api";
import router from "@/router";

export default {
  name: "ThreadCard",
  components: { ThreadDialog },
  data() {
    return {
      dialogVisible: false,
    };
  },
  props: {
    thread: {},
  },
  methods: {
    deleteThread() {
      if (confirm("Are you sure you want to delete this thread?")) {
        api.threads
          .delete(this.thread.id)
          .then(() => {
            this.$emit("refresh");
          })
          .catch((error) => {
            alert(error.response.data);
          });
      }
    },
    async refresh() {
      this.board = await api.threads.getThread(this.thread.id);
    },
    closeDialog() {
      this.dialogVisible = false;
      this.refresh();
    },
    goToThread() {
      router.push({
        name: "Posts",
        params: { thread: this.thread },
      });
    },
    file() {
      return require("@/../../media/" + this.thread.media[0].fileName);
    },
  },
  computed: {
    isMod() {
      return this.$store.getters["auth/isMod"];
    },
    hasMedia() {
      return this.thread.media !== null && this.thread.media.length > 0;
    },
  },
};
</script>

<style scoped></style>
