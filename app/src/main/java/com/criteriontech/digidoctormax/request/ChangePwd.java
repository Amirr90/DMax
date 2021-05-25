package com.criteriontech.digidoctormax.request;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class ChangePwd extends BaseObservable {
    private Integer id;
    private Integer serviceProviderTypeId;
    private String oldPassword;
    private String newPassword;
    private String userMobileNo;
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public Integer getServiceProviderTypeId() {
        return serviceProviderTypeId;
    }

    public void setServiceProviderTypeId(Integer serviceProviderTypeId) {
        this.serviceProviderTypeId = serviceProviderTypeId;
        notifyPropertyChanged(BR.serviceProviderLoginId);
    }

    @Bindable
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Bindable
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Bindable
    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }
}
