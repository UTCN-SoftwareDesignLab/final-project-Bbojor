<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="610"
    :value="opened"
    :persistent="true"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Schedule consultation" : "Edit consultation" }}
        </v-toolbar>
        <v-form>
          <v-text-field
            v-if="!isNew"
            v-model="consultation.patientName"
            label="Patient"
            :readonly="true"
          />
          <v-text-field
            v-if="!isNew"
            v-model="consultation.doctorName"
            label="Doctor"
            :readonly="true"
          />
          <v-select
            v-model="selectedDoctor"
            v-if="isSecretary && isNew"
            item-text="username"
            item-value="id"
            :items="doctorList"
          >
          </v-select>
          <v-text-field
            v-if="isSecretary && isNew"
            type="date"
            v-model="selectedDate"
            label="Date"
          />
          <v-time-picker
            v-if="isSecretary && isNew"
            :allowed-hours="[8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]"
            :scrollable="true"
            v-model="selectedTime"
            :allowed-minutes="[0]"
            format="24hr"
          ></v-time-picker>
          <v-textarea v-model="consultation.details" v-if="!isSecretary">
            Description
          </v-textarea>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist" v-if="!isSecretary || isNew">
            {{ isNew ? "Add" : "Save" }}
          </v-btn>
          <v-btn v-if="isSecretary && !isNew" @click="deleteConsultation"
            >Delete</v-btn
          >
          <v-btn v-if="isSecretary && !isNew" @click="checkIn">Check In</v-btn>
          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ConsultationDialog",
  data() {
    return {
      selectedDoctor: -1,
      selectedDate: null,
      selectedTime: null,
    };
  },
  props: {
    consultation: Object,
    doctorList: Array,
    opened: Boolean,
    patientId: Number,
  },
  methods: {
    persist() {
      if (this.isNew) {
        var fullDate = new Date(
          this.selectedDate + " " + this.selectedTime + ":00"
        );
        api.consultations
          .create({
            id: this.consultation.id,
            patientId: this.patientId,
            doctorId: this.selectedDoctor,
            date: fullDate,
            details: this.consultation.details,
          })
          .then(() => {
            this.$emit("refresh");
            this.scheduled();
          })
          .catch((error) => {
            alert(error.response.data);
          });
      } else {
        api.consultations
          .edit({
            id: this.consultation.id,
            patientId: this.patientId,
            doctorId: this.consultation.id,
            date: this.consultation.date,
            details: this.consultation.details,
          })
          .then(() => this.$emit("close-dialog"))
          .catch((error) => {
            alert(error.response.data);
          });
      }
    },
    deleteConsultation() {
      api.consultations
        .delete(this.consultation.id)
        .then(() => this.$emit("refresh"));
    },
    close() {
      this.$emit("close-dialog");
    },
    scheduled() {
      var fullDate = new Date(
        this.selectedDate + " " + this.selectedTime + ":00"
      );
      const jsonString = JSON.stringify({
        doctorName: this.selectedDoctor,
        patientName: this.patientId,
        date: fullDate,
      });

      api.websocket.sendMessage(jsonString, "/api/notifications/schedule");
    },
    checkIn() {

      const jsonString = JSON.stringify({
        doctorName: this.consultation.doctorId,
        patientName: this.consultation.patientName,
        date: this.consultation.date,
      });

      api.websocket.sendMessage(jsonString, "/api/notifications/check-in");
    },
  },
  computed: {
    isNew: function () {
      return !this.consultation.id;
    },
    isSecretary() {
      return this.$store.getters["auth/isSecretary"];
    },
  },
};
</script>

<style scoped></style>
