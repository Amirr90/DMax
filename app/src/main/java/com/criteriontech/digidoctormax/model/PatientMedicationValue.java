package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientMedicationValue {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("memberId")
    @Expose
    public Integer memberId;
    @SerializedName("isMonitoring")
    @Expose
    public Boolean isMonitoring;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("problemName")
    @Expose
    public String problemName;
    @SerializedName("downloadStatus")
    @Expose
    public Integer downloadStatus;
    @SerializedName("speciality")
    @Expose
    public String speciality;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("location")
    @Expose
    public String location;
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
    @SerializedName("consultantType")
    @Expose
    public String consultantType;
    @SerializedName("type")
    @Expose
    public Integer type;
    public String getGenAge(){
        return gender+", "+age;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Boolean getMonitoring() {
        return isMonitoring;
    }

    public void setMonitoring(Boolean monitoring) {
        isMonitoring = monitoring;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public Integer getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(Integer downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getLocation() {
        return location+", "+address;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getConsultantType() {
        return consultantType;
    }

    public void setConsultantType(String consultantType) {
        this.consultantType = consultantType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
