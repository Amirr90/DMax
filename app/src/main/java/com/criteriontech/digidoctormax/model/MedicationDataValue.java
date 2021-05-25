package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicationDataValue {
    @SerializedName("medicineList")
    @Expose
    public List<MedicineList> medicineList = null;
    @SerializedName("dosageFormList")
    @Expose
    public Object dosageFormList;
    @SerializedName("unitList")
    @Expose
    public Object unitList;
    @SerializedName("frequencyList")
    @Expose
    public List<FrequencyList> frequencyList = null;

    public List<MedicineList> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<MedicineList> medicineList) {
        this.medicineList = medicineList;
    }

    public Object getDosageFormList() {
        return dosageFormList;
    }

    public void setDosageFormList(Object dosageFormList) {
        this.dosageFormList = dosageFormList;
    }

    public Object getUnitList() {
        return unitList;
    }

    public void setUnitList(Object unitList) {
        this.unitList = unitList;
    }

    public List<FrequencyList> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(List<FrequencyList> frequencyList) {
        this.frequencyList = frequencyList;
    }
}
