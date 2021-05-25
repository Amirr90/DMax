package com.criteriontech.digidoctormax.request;

public class ClinicRegistration {
    private long callingCodeId;
    private long serviceProviderTypeId;
    private String name;
    private String mobileNo;
    private String emailId;
    private String address;
    private String password;
    private String otp;
    private String deviceToken;
    private long deviceType;
    private String registrationNo;
    private String appType;
    private String workDescription;

    public long getCallingCodeId() {
        return callingCodeId;
    }

    public void setCallingCodeId(long callingCodeId) {
        this.callingCodeId = callingCodeId;
    }

    public long getServiceProviderTypeId() {
        return serviceProviderTypeId;
    }

    public void setServiceProviderTypeId(long serviceProviderTypeId) {
        this.serviceProviderTypeId = serviceProviderTypeId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public long getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(long deviceType) {
        this.deviceType = deviceType;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }
}
