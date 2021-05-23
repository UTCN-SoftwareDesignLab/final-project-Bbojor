<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="700"
    :value="opened"
    :persistent="true"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Add new Patient" : "Edit patient data" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="patient.name" label="Name" />
          <v-text-field
            v-mask="'#############'"
            v-model="patient.personalNumericalCode"
            label="Personal Numerical Code"
          />
          <v-text-field
            v-mask="'######'"
            v-model="patient.identityCardNumber"
            label="Identity Card Number"
          />
          <v-text-field
            type="date"
            v-model="patient.birthDate"
            label="Birth Date"
          />
          <v-text-field v-model="patient.address" label="Address" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Add" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deletePatient">Delete</v-btn>
          <v-btn v-if="!isNew" @click="scheduleConsultation">Schedule Consultation</v-btn>
          <v-btn v-if="!isNew" @click="viewConsultations">View Consultations</v-btn>
          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import router from "../router";

export default {
  name: "PatientDialog",
  props: {
    patient: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.patients
          .create({
            id: this.patient.id,
            name: this.patient.name,
            identityCardNumber: this.patient.identityCardNumber,
            personalNumericalCode: this.patient.personalNumericalCode,
            birthDate: this.patient.birthDate,
            address: this.patient.address,
          })
          .then(() => this.$emit("refresh"))
          .catch((error) => {
            alert(error.response.data);
          });
      } else {
        api.patients
          .edit({
            id: this.patient.id,
            name: this.patient.name,
            identityCardNumber: this.patient.identityCardNumber,
            personalNumericalCode: this.patient.personalNumericalCode,
            birthDate: this.patient.birthDate,
            address: this.patient.address,
          })
          .then(() => this.$emit("close-dialog"))
          .catch((error) => {
            alert(error.response.data);
          });
      }
    },
    deletePatient() {
      api.patients.delete(this.patient.id).then(() => this.$emit("refresh"));
    },
    close() {
      this.$emit("close-dialog");
    },
    viewConsultations() {
      router.push({
        name: "Consultations",
        params: { patientId: this.patient.id, showDialog: false },
      });
    },
    scheduleConsultation() {
      router.push({
        name: "Consultations",
        params: { patientId: this.patient.id, showDialog: true },
      });
    },
  },
  computed: {
    isNew: function () {
      return !this.patient.id;
    },
  },
};
</script>

<style scoped></style>
