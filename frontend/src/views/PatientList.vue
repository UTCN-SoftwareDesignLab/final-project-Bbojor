<template>
  <v-card>
    <v-card-title>
      Patients
      <v-spacer></v-spacer>
      <v-text-field
        label="Name"
        single-line
        v-model="searchParams.name"
        hide-details
      ></v-text-field>
      <v-btn @click="searchPatients" icon> <v-icon>mdi-magnify </v-icon></v-btn>
      <v-btn @click="addPatient">Add Patient</v-btn>
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
    <PatientDialog
      :opened="editDialogVisible"
      :patient="selectedPatient"
      @refresh="refreshList"
      @close-dialog="closeDialog"
    ></PatientDialog>
  </v-card>
</template>

<script>
import api from "../api";
import PatientDialog from "../components/PatientDialog";
import moment from "moment";

export default {
  name: "PatientList",
  components: { PatientDialog },
  data() {
    return {
      items: [],
      searchParams: {
        name: "",
      },
      headers: [
        {
          text: "Name",
          align: "start",
          value: "name",
        },
        {
          text: "Birth Date",
          value: "birthDate",
          formatter: function (date) {
            return moment(date).format("YYYY-MM-DD");
          },
        },
        { text: "Identity Card Number", value: "identityCardNumber" },
        { text: "Personal Numerical Code", value: "personalNumericalCode" },
        { text: "Address", value: "address" },
      ],
      editDialogVisible: false,
      selectedPatient: {},
    };
  },
  methods: {
    openDialog(patient) {
      this.selectedPatient = patient;
      this.editDialogVisible = true;
    },
    addPatient() {
      this.selectedPatient = {};
      this.editDialogVisible = true;
    },
    async refreshList() {
      this.selectedPatient = {};
      let searchString = "";
      if (this.searchParams.name)
        searchString += "name=" + this.searchParams.name;
      if (searchString === "") this.items = await api.patients.allPatients();
      else this.items = await api.patients.allPatientsFiltered(searchString);
    },
    searchPatients() {
      this.refreshList();
    },
    closeDialog() {
      this.editDialogVisible = false;
      this.refreshList();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
