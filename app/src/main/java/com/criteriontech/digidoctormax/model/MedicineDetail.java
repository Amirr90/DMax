package com.criteriontech.digidoctormax.model;

import androidx.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineDetail {
    @SerializedName("medicineName")
    @Expose
    public String medicineName;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("strength")
    @Expose
    public Double strength;
    @SerializedName("doseUnitId")
    @Expose
    public Integer doseUnitId;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("dosageId")
    @Expose
    public Integer dosageId;
    @SerializedName("frequencyId")
    @Expose
    public Integer frequencyId;
    @SerializedName("frequencyName")
    @Expose
    public String frequencyName;
    @SerializedName("formName")
    @Expose
    public String formName;
    public String duration;
    public String remark;

    @Override
    public String toString() {
        return medicineName;
    }

    public MedicineDetail(String medicineName, Integer id, Double strength, Integer doseUnitId, String unitName, Integer dosageId, Integer frequencyId, String frequencyName, String formName, String duration, String remark) {
        this.medicineName = medicineName;
        this.id = id;
        this.strength = strength;
        this.doseUnitId = doseUnitId;
        this.unitName = unitName;
        this.dosageId = dosageId;
        this.frequencyId = frequencyId;
        this.frequencyName = frequencyName;
        this.formName = formName;
        this.duration=duration;
        this.remark=remark;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
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

    public Integer getDosageId() {
        return dosageId;
    }

    public void setDosageId(Integer dosageId) {
        this.dosageId = dosageId;
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

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
