package com.criteriontech.digidoctormax.model;

public class ClinicVisitedPatientModel {
    public Integer totalPatientVisited;
    public Integer totalWritePrescription;
    public Integer totalCancelledAppointments;

    public Integer getTotalPatientVisited() {
        return totalPatientVisited;
    }

    public void setTotalPatientVisited(Integer totalPatientVisited) {
        this.totalPatientVisited = totalPatientVisited;
    }

    public Integer getTotalWritePrescription() {
        return totalWritePrescription;
    }

    public void setTotalWritePrescription(Integer totalWritePrescription) {
        this.totalWritePrescription = totalWritePrescription;
    }

    public Integer getTotalCancelledAppointments() {
        return totalCancelledAppointments;
    }

    public void setTotalCancelledAppointments(Integer totalCancelledAppointments) {
        this.totalCancelledAppointments = totalCancelledAppointments;
    }
}
