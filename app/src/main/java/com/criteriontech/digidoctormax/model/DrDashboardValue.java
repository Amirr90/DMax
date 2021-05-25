package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrDashboardValue {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("noOfpatients")
    @Expose
    public Integer noOfpatients;
    @SerializedName("totalCollectedFees")
    @Expose
    public Integer totalCollectedFees;
    @SerializedName("prescribedPatients")
    @Expose
    public Integer prescribedPatients;
    @SerializedName("pendingPatients")
    @Expose
    public Integer pendingPatients;
    @SerializedName("todaysAppointments")
    @Expose
    public Integer todaysAppointments;
    @SerializedName("totalAppointments")
    @Expose
    public Integer totalAppointments;
    @SerializedName("totalWritePrescription")
    @Expose
    public Integer totalWritePrescription;
    @SerializedName("totalCancelledAppointments")
    @Expose
    public Integer totalCancelledAppointments;
    @SerializedName("totalPendingAppointments")
    @Expose
    public Integer totalPendingAppointments;
    @SerializedName("incomeList")
    @Expose
    public List<IncomeList> incomeList = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public Integer getNoOfpatients() {
        return noOfpatients;
    }

    public void setNoOfpatients(Integer noOfpatients) {
        this.noOfpatients = noOfpatients;
    }

    public Integer getTotalCollectedFees() {
        return totalCollectedFees;
    }

    public void setTotalCollectedFees(Integer totalCollectedFees) {
        this.totalCollectedFees = totalCollectedFees;
    }

    public Integer getPrescribedPatients() {
        return prescribedPatients;
    }

    public void setPrescribedPatients(Integer prescribedPatients) {
        this.prescribedPatients = prescribedPatients;
    }

    public Integer getPendingPatients() {
        return pendingPatients;
    }

    public void setPendingPatients(Integer pendingPatients) {
        this.pendingPatients = pendingPatients;
    }

    public Integer getTodaysAppointments() {
        return todaysAppointments;
    }

    public void setTodaysAppointments(Integer todaysAppointments) {
        this.todaysAppointments = todaysAppointments;
    }

    public Integer getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(Integer totalAppointments) {
        this.totalAppointments = totalAppointments;
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

    public Integer getTotalPendingAppointments() {
        return totalPendingAppointments;
    }

    public void setTotalPendingAppointments(Integer totalPendingAppointments) {
        this.totalPendingAppointments = totalPendingAppointments;
    }

    public List<IncomeList> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(List<IncomeList> incomeList) {
        this.incomeList = incomeList;
    }
}
