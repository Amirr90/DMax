package com.criteriontech.digidoctormax.model;

import com.criteriontech.digidoctormax.utils.AppUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientMedicationDetailValue {
    @SerializedName("appointmentId")
    @Expose
    public String appointmentId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("problemName")
    @Expose
    public String problemName;
    @SerializedName("testName")
    @Expose
    public String testName;
    @SerializedName("drName")
    @Expose
    public String drName;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("filePath")
    @Expose
    public String filePath;
    @SerializedName("downloadStatus")
    @Expose
    public String downloadStatus;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("speciality")
    @Expose
    public String speciality;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("vitalDetails")
    @Expose
    public String vitalDetails;
    @SerializedName("medicineDetails")
    @Expose
    public List<PatientMedicineDetail> medicineDetails = null;
    @SerializedName("adviseDetails")
    @Expose
    public List<AdviseDetail> adviseDetails = null;
    @SerializedName("isMonitoring")
    @Expose
    public String isMonitoring;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("appointmentFees")
    @Expose
    public String appointmentFees;
    @SerializedName("appointDate")
    @Expose
    public String appointDate;
    @SerializedName("appointTime")
    @Expose
    public String appointTime;
    @SerializedName("dayName")
    @Expose
    public String dayName;
    @SerializedName("paymentStatus")
    @Expose
    public String paymentStatus;

    public String getGenAge(){
        return gender+", "+age;
    }
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getVitalDetails() {
        return vitalDetails;
    }

    public void setVitalDetails(String vitalDetails) {
        this.vitalDetails = vitalDetails;
    }

    public List<PatientMedicineDetail> getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(List<PatientMedicineDetail> medicineDetails) {
        this.medicineDetails = medicineDetails;
    }

    public List<AdviseDetail> getAdviseDetails() {
        return adviseDetails;
    }

    public void setAdviseDetails(List<AdviseDetail> adviseDetails) {
        this.adviseDetails = adviseDetails;
    }

    public String getIsMonitoring() {
        return isMonitoring;
    }

    public void setIsMonitoring(String isMonitoring) {
        this.isMonitoring = isMonitoring;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAppointmentFees() {
        if(appointmentFees==null)
            return AppUtils.getCurrencyFormat(0);
        return AppUtils.getCurrencyFormat1(Float.parseFloat(appointmentFees));
    }

    public void setAppointmentFees(String appointmentFees) {
        this.appointmentFees = appointmentFees;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
