package com.criteriontech.digidoctormax.request;

public class DrRegistration {
    private String mobileNo;
    private String clinicName;
    private String doctorName;
    private String emailId;
    private String password;
    private String otp;
    private String countryCallingCode;
    private String deviceToken;
    private long deviceType;
    private String specialities;
    private String experience;
    private String degree;
    private String regNo;
    private String fee;
    private String workDescription;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
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
}
