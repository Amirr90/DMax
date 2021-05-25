package com.criteriontech.digidoctormax.model;

import com.criteriontech.digidoctormax.utils.AppUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {
    @SerializedName("memberId")
    @Expose
    public Integer memberId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("paymentAmount")
    @Expose
    public Float paymentAmount;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("signSymptom")
    @Expose
    public String signSymptom;
    @SerializedName("signSymptomNew")
    @Expose
    public String signSymptomNew;
    @SerializedName("filePath")
    @Expose
    public String filePath;
    @SerializedName("vitalDetails")
    @Expose
    public Object vitalDetails;
    @SerializedName("isMonitoring")
    @Expose
    public Boolean isMonitoring;

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getPaymentAmount() {
        return AppUtils.getCurrencyFormat(paymentAmount);
    }

    public void setPaymentAmount(Float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getGenAge(){
        return gender+", "+age;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public String getSignSymptom() {
        return signSymptom;
    }

    public void setSignSymptom(String signSymptom) {
        this.signSymptom = signSymptom;
    }

    public String getSignSymptomNew() {
        return signSymptomNew;
    }

    public void setSignSymptomNew(String signSymptomNew) {
        this.signSymptomNew = signSymptomNew;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Object getVitalDetails() {
        return vitalDetails;
    }

    public void setVitalDetails(Object vitalDetails) {
        this.vitalDetails = vitalDetails;
    }

    public Boolean getMonitoring() {
        return isMonitoring;
    }

    public void setMonitoring(Boolean monitoring) {
        isMonitoring = monitoring;
    }
}
