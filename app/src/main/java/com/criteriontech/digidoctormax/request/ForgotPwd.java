package com.criteriontech.digidoctormax.request;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.criteriontech.digidoctormax.BR;

public class ForgotPwd extends BaseObservable {
    private String mobileNo;
    private Integer serviceProviderTypeId;
    private String otp;
    private String password;
    private String confirmPassword;

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public String getMobileNo() {
        return  mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        notifyPropertyChanged(BR.mobileNo);
    }

    @Bindable
    public Integer getServiceProviderTypeId() {
        return serviceProviderTypeId;
    }

    public void setServiceProviderTypeId(Integer serviceProviderTypeId) {
        this.serviceProviderTypeId = serviceProviderTypeId;
    }

    @Bindable
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
