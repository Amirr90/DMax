package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPatientMedicationMedicineModel {

    @SerializedName("medicineName")
    @Expose
    private String medicineName;
    @SerializedName("dosageFormName")
    @Expose
    private String dosageFormName;
    @SerializedName("strength")
    @Expose
    private String strength;
    @SerializedName("unitName")
    @Expose
    private String unitName;
    @SerializedName("frequencyName")
    @Expose
    private String frequencyName;
    @SerializedName("durationInDays")
    @Expose
    private String durationInDays;
    @SerializedName("endDate")
    @Expose
    private String endDate;

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
