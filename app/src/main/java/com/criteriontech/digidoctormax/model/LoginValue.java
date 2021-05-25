package com.criteriontech.digidoctormax.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginValue extends BaseObservable {
    String registrationNo;
    String workDescription;
    Integer serviceProviderDetailsId;

    public Integer getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(Integer serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

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
    public String serviceProvidertype;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("primaryStatus")
    @Expose
    public Integer primaryStatus;
    @SerializedName("countryId")
    @Expose
    public Integer countryId;
    @SerializedName("stateId")
    @Expose
    public Integer stateId;
    @SerializedName("districtId")
    @Expose
    public Integer districtId;
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
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("isEraUser")
    @Expose
    public Integer isEraUser;
    @SerializedName("countryCallingCode")
    @Expose
    public String countryCallingCode;
    @SerializedName("clinicDetails")
    @Expose
    public String clinicDetails;
    @SerializedName("doctorsCount")
    @Expose
    public Integer doctorsCount;
    @SerializedName("isCovid")
    @Expose
    public Integer isCovid;
    @SerializedName("gender")
    @Expose
    public Object gender;
    @SerializedName("dob")
    @Expose
    public Object dob;
    @SerializedName("isExists")
    @Expose
    public Object isExists;
    @SerializedName("uniqueNo")

    @Expose
    public Object uniqueNo;

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public Integer getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Integer userLoginId) {
        this.userLoginId = userLoginId;
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
    public String getServiceProvidertype() {
        return serviceProvidertype;
    }

    public void setServiceProvidertype(String serviceProvidertype) {
        this.serviceProvidertype = serviceProvidertype;
    }

    @Bindable
    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    @Bindable
    public Integer getPrimaryStatus() {
        return primaryStatus;
    }

    public void setPrimaryStatus(Integer primaryStatus) {
        this.primaryStatus = primaryStatus;
    }

    @Bindable
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsEraUser() {
        return isEraUser;
    }

    public void setIsEraUser(Integer isEraUser) {
        this.isEraUser = isEraUser;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public String getClinicDetails() {
        return clinicDetails;
    }

    public void setClinicDetails(String clinicDetails) {
        this.clinicDetails = clinicDetails;
    }

    public Integer getDoctorsCount() {
        return doctorsCount;
    }

    public void setDoctorsCount(Integer doctorsCount) {
        this.doctorsCount = doctorsCount;
    }

    public Integer getIsCovid() {
        return isCovid;
    }

    public void setIsCovid(Integer isCovid) {
        this.isCovid = isCovid;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Object getIsExists() {
        return isExists;
    }

    public void setIsExists(Object isExists) {
        this.isExists = isExists;
    }

    public Object getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(Object uniqueNo) {
        this.uniqueNo = uniqueNo;
    }
}
