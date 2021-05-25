package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("medicineName")
    @Expose
    public String medicineName;
    @SerializedName("strength")
    @Expose
    public Integer strength;
    @SerializedName("doseUnitId")
    @Expose
    public Integer doseUnitId;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("frequencyId")
    @Expose
    public Integer frequencyId;
    @SerializedName("frequencyName")
    @Expose
    public String frequencyName;
    @SerializedName("dosageId")
    @Expose
    public Integer dosageId;
    @SerializedName("formName")
    @Expose
    public String formName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getDoseUnitId() {
        return doseUnitId;
    }

    public void setDoseUnitId(Integer doseUnitId) {
        this.doseUnitId = doseUnitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(Integer frequencyId) {
        this.frequencyId = frequencyId;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }

    public Integer getDosageId() {
        return dosageId;
    }

    public void setDosageId(Integer dosageId) {
        this.dosageId = dosageId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
