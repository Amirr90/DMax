package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClinicDashboardValue {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("isProfileCompleted")
    @Expose
    public Integer isProfileComplete;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("noOfDoctors")
    @Expose
    public Integer noOfDoctors;
    @SerializedName("totalCollectedFees")
    @Expose
    public Integer totalCollectedFees;
    @SerializedName("totalPatientVisited")
    @Expose
    public Integer totalPatientVisited;
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

    public Integer getIsProfileComplete() {
        return isProfileComplete;
    }

    public void setIsProfileComplete(Integer isProfileComplete) {
        this.isProfileComplete = isProfileComplete;
    }

    @SerializedName("incomeList")

    @Expose
    public List<IncomeList> incomeList = null;

    public Integer getNoOfDoctors() {
        return noOfDoctors;
    }

    public void setNoOfDoctors(Integer noOfDoctors) {
        this.noOfDoctors = noOfDoctors;
    }

    public Integer getTotalCollectedFees() {
        return totalCollectedFees;
    }

    public void setTotalCollectedFees(Integer totalCollectedFees) {
        this.totalCollectedFees = totalCollectedFees;
    }

    public Integer getTotalPatientVisited() {
        return totalPatientVisited;
    }

    public void setTotalPatientVisited(Integer totalPatientVisited) {
        this.totalPatientVisited = totalPatientVisited;
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
}
