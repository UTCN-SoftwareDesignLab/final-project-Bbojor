<template>
  <v-container class="float-left">
    <v-img v-if="fullSize" @click="toggleSize()" class="image" :src="file()" />
    <v-img
      v-if="!fullSize"
      @click="toggleSize()"
      width="100"
      height="100"
      class="image"
      :src="file()"
    />
  </v-container>
</template>

<script>
export default {
  name: "ToggleableImage",
  props: {
    src: null,
  },
  data() {
    return {
      fullSize: false,
    };
  },
  methods: {
    toggleSize() {
      this.fullSize = !this.fullSize;
    },
    file() {
      return require("@/../../media/" + this.src);
    },
  },
};
</script>

<style scoped></style>
