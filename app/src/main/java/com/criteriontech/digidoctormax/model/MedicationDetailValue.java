package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicationDetailValue {
    @SerializedName("userDetails")
    @Expose
    public List<UserDetail> userDetails = null;
    @SerializedName("suggestedTestDetails")
    @Expose
    public List<SuggestedTestDetail> suggestedTestDetails = null;
    @SerializedName("medicineDetails")
    @Expose
    public List<MedicineDetail> medicineDetails = null;
    @SerializedName("dosageFormDetails")
    @Expose
    public List<DosageFormDetail> dosageFormDetails = null;
    @SerializedName("frequencyDetails")
    @Expose
    public List<FrequencyList> frequencyDetails = null;

    public List<UserDetail> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<UserDetail> userDetails) {
        this.userDetails = userDetails;
    }

    public List<SuggestedTestDetail> getSuggestedTestDetails() {
        return suggestedTestDetails;
    }

    public void setSuggestedTestDetails(List<SuggestedTestDetail> suggestedTestDetails) {
        this.suggestedTestDetails = suggestedTestDetails;
    }

    public List<MedicineDetail> getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(List<MedicineDetail> medicineDetails) {
        this.medicineDetails = medicineDetails;
    }

    public List<DosageFormDetail> getDosageFormDetails() {
        return dosageFormDetails;
    }

    public void setDosageFormDetails(List<DosageFormDetail> dosageFormDetails) {
        this.dosageFormDetails = dosageFormDetails;
    }

    public List<FrequencyList> getFrequencyDetails() {
        return frequencyDetails;
    }

    public void setFrequencyDetails(List<FrequencyList> frequencyDetails) {
        this.frequencyDetails = frequencyDetails;
    }
}
