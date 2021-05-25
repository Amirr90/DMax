package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicRegistrationValue {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("userLoginId")
    @Expose
    public Integer userLoginId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("emailId")
    @Expose
    public String emailId;
    @SerializedName("serviceProviderType")
    @Expose
    public Object serviceProviderType;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("districtId")
    @Expose
    public Integer districtId;
    @SerializedName("stateId")
    @Expose
    public Integer stateId;
    @SerializedName("countryId")
    @Expose
    public Integer countryId;
    @SerializedName("primaryStatus")
    @Expose
    public Integer primaryStatus;
    @SerializedName("countryName")
    @Expose
    public String countryName;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("cityName")
    @Expose
    public String cityName;
    @SerializedName("pinCode")
    @Expose
    public String pinCode;
    @SerializedName("profilePhotoPath")
    @Expose
    public Object profilePhotoPath;
    @SerializedName("countryCallingCode")
    @Expose
    public String countryCallingCode;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("dob")
    @Expose
    public Object dob;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Integer userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Object getServiceProviderType() {
        return serviceProviderType;
    }

    public void setServiceProviderType(Object serviceProviderType) {
        this.serviceProviderType = serviceProviderType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getPrimaryStatus() {
        return primaryStatus;
    }

    public void setPrimaryStatus(Integer primaryStatus) {
        this.primaryStatus = primaryStatus;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Object getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(Object profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }
}
