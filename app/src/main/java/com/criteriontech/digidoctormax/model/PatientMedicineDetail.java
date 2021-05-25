package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientMedicineDetail {
    @SerializedName("appointmentId")
    @Expose
    public String appointmentId;
    @SerializedName("medicineName")
    @Expose
    public String medicineName;
    @SerializedName("dosageFormName")
    @Expose
    public String dosageFormName;
    @SerializedName("strength")
    @Expose
    public String strength;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("frequencyName")
    @Expose
    public String frequencyName;
    @SerializedName("durationInDays")
    @Expose
    public String durationInDays;
    @SerializedName("endDate")
    @Expose
    public String endDate;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosageFormName() {
        return dosageFormName;
    }

    public void setDosageFormName(String dosageFormName) {
        this.dosageFormName = dosageFormName;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }

    public String getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(String durationInDays) {
        this.durationInDays = durationInDays;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
