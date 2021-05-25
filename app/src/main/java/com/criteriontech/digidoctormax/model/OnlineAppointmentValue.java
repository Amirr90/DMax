package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineAppointmentValue {
    @SerializedName("callingStatus")
    @Expose
    public Integer callingStatus;
    @SerializedName("appointmentId")
    @Expose
    public Integer appointmentId;
    @SerializedName("memberId")
    @Expose
    public Integer memberId;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("guardianTypeId")
    @Expose
    public Integer guardianTypeId;
    @SerializedName("guardianName")
    @Expose
    public String guardianName;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("cityName")
    @Expose
    public String cityName;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("appointDate")
    @Expose
    public String appointDate;
    @SerializedName("appointTime")
    @Expose
    public String appointTime;
    @SerializedName("isPrescribed")
    @Expose
    public Boolean isPrescribed;
    @SerializedName("appointmentIdView")
    @Expose
    public String appointmentIdView;
    @SerializedName("filePath")
    @Expose
    public String filePath;
    @SerializedName("expiredStatus")
    @Expose
    public Integer expiredStatus;
    @SerializedName("problemName")
    @Expose
    public String problemName;
    @SerializedName("visitType")
    @Expose
    public String visitType;

    public String getGenAge(){
        return gender+", "+age;
    }

    public Integer getCallingStatus() {
        return callingStatus;
    }

    public void setCallingStatus(Integer callingStatus) {
        this.callingStatus = callingStatus;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getGuardianTypeId() {
        return guardianTypeId;
    }

    public void setGuardianTypeId(Integer guardianTypeId) {
        this.guardianTypeId = guardianTypeId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public Boolean getPrescribed() {
        return isPrescribed;
    }

    public void setPrescribed(Boolean prescribed) {
        isPrescribed = prescribed;
    }

    public String getAppointmentIdView() {
        return appointmentIdView;
    }

    public void setAppointmentIdView(String appointmentIdView) {
        this.appointmentIdView = appointmentIdView;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getExpiredStatus() {
        return expiredStatus;
    }

    public void setExpiredStatus(Integer expiredStatus) {
        this.expiredStatus = expiredStatus;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }
}
