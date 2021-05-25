package com.criteriontech.digidoctormax.request;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.criteriontech.digidoctormax.utils.AppUtils;

public class DoctorProfileValue extends BaseObservable {
    private Integer id;
    private String name;
    private String mobileNo;
    private String emailId;
    private String address;
    private String password;
    private Integer gender;
    private Integer serviceProviderTypeId;//dr=3, clinic=3
    private Integer otp;
    private String registrationNo;
    private String degree;
    private String yearOfExperience;
    private Integer drFee;
    private String profilePhotoPath;
    private String workDescription;
    private String doctorName;
    private Integer duration;
    private String dtDataTable;
    public String userMobileNo;
    @Bindable
    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Bindable
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Bindable
    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    @Bindable
    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    @Bindable
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Bindable
    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(String yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    @Bindable
    public String getDrFee() {
        return AppUtils.getCurrencyFormat(drFee);
    }

    @Bindable
    public void setDrFee(String drFee) {
        this.drFee = Integer.valueOf(drFee);
    }

    @Bindable
    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    @Bindable
    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    @Bindable
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Bindable
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Bindable
    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }
}
