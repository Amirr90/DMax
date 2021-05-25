package com.criteriontech.digidoctormax.request;

public class GenerateOTP {
    private Integer callingCodeID;
    private Integer serviceProviderTypeID;
    private String mobileNo;

    public Integer getCallingCodeID() {
        if (null == callingCodeID)
            return 101;
        else return callingCodeID;
    }

    public void setCallingCodeID(Integer callingCodeID) {
        this.callingCodeID = callingCodeID;
    }

    public Integer getServiceProviderTypeID() {
        if (serviceProviderTypeID == null)
            return 0;
        else return serviceProviderTypeID;
    }

    public void setServiceProviderTypeID(Integer serviceProviderTypeID) {
        this.serviceProviderTypeID = serviceProviderTypeID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
