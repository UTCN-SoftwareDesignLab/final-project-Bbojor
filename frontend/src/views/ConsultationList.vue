<template>
  <v-card>
    <v-card-title>
      Consultations
      <v-spacer></v-spacer>
    </v-card-title>
    <v-data-table :headers="headers" :items="items" @click:row="openDialog">
      <template
        v-for="header in headers.filter((header) =>
          header.hasOwnProperty('formatter')
        )"
        v-slot:[`item.${header.value}`]="{ header, value }"
      >
        {{ header.formatter(value) }}
      </template>
    </v-data-table>
    <ConsultationDialog
      :opened="editDialogVisible"
      :patientId="this.$route.params.patientId"
      :consultation="selectedConsultation"
      :doctor-list="doctorList"
      @refresh="refreshList"
      @close-dialog="closeDialog"
    ></ConsultationDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ConsultationDialog from "../components/ConsultationDialog";
import moment from "moment";

export default {
  name: "ConsultationList",
  components: { ConsultationDialog },
  props: {
    patientId: String,
    showDialog: Boolean,
  },
  data() {
    return {
      items: [],
      headers: [
        {
          text: "Date",
          value: "date",
          formatter: function (date) {
            return moment(date).format("YYYY-MM-DD HH");
          },
        },
        { text: "Doctor", value: "doctorName" },
        { text: "Patient", value: "patientName" },
        { text: "Details", value: "details" },
      ],
      editDialogVisible: false,
      selectedConsultation: {},
      doctorList: [],
    };
  },
  methods: {
    async openDialog(consultation) {
      if (!this.isSecretary) {
        //if doctor
        api.websocket.connect("/user/" + this.userId + "/message");
      } else api.websocket.connect();
      this.selectedConsultation = consultation;
      this.editDialogVisible = true;
      this.doctorList = await api.users.allUsersByRole("DOCTOR");
    },
    addPatient() {
      this.selectedPatient = {};
      this.editDialogVisible = true;
    },
    async refreshList() {
      this.selectedPatient = {};
      if (this.isSecretary)
        this.items = await api.consultations.allConsultationsFiltered(
          "patientId=" + this.$route.params.patientId
        );
      else
        this.items = await api.consultations.allConsultationsFiltered(
          "doctorId=" + this.userId
        );
    },
    closeDialog() {
      this.editDialogVisible = false;
      api.websocket.disconnect();
      this.refreshList();
    },
  },
  computed: {
    isSecretary() {
      return this.$store.getters["auth/isSecretary"];
    },
    userId() {
      return this.$store.getters["auth/userId"];
    },
  },
  created() {
    this.refreshList();
    if (this.isSecretary && this.$route.params.showDialog) this.openDialog({});
  },
};
</script>

<style scoped></style>
