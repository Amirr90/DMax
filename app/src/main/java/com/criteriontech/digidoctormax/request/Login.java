package com.criteriontech.digidoctormax.request;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;

import com.criteriontech.digidoctormax.BR;

public class Login {
    private String mobileNo;
    private String password;
    private String accessToken;
    private String serviceProviderTypeID;
    private String deviceToken;
    private String deviceType;
    private String appType;
    private String uniqueNOS;
    private String otp;
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getServiceProviderTypeID() {
        return serviceProviderTypeID;
    }

    public void setServiceProviderTypeID(String serviceProviderTypeID) {
        this.serviceProviderTypeID = serviceProviderTypeID;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getUniqueNOS() {
        return uniqueNOS;
    }

    public void setUniqueNOS(String uniqueNOS) {
        this.uniqueNOS = uniqueNOS;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
