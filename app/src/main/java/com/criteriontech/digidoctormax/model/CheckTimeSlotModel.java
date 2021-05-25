package com.criteriontech.digidoctormax.model;

public class CheckTimeSlotModel {

    private String memberId;
    private String serviceProviderDetailsId;
    private String appointDate;
    private String appointTime;
    private String userMobileNo;
    private String isEraUser;
    private String appointmentId;
    private boolean isRevisit;

    public boolean getIsRevisit() {
        return isRevisit;
    }

    public void setIsRevisit(boolean isRevisit) {
        this.isRevisit = isRevisit;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(String serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getIsEraUser() {
        return isEraUser;
    }

    public void setIsEraUser(String isEraUser) {
        this.isEraUser = isEraUser;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }


    @Override
    public String toString() {
        return "CheckTimeSlotModel{" +
                "memberId='" + memberId + '\'' +
                ", serviceProviderDetailsId='" + serviceProviderDetailsId + '\'' +
                ", appointDate='" + appointDate + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", userMobileNo='" + userMobileNo + '\'' +
                ", isEraUser='" + isEraUser + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", isRevisit='" + isRevisit + '\'' +
                '}';
    }
}
