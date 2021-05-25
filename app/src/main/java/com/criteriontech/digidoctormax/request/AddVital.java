package com.criteriontech.digidoctormax.request;

public class AddVital {
    private Integer memberId;
    private long vitalID;
    private String date;
    private String time;
    private String dtDataTable;
    private Integer serviceProviderDetailsId;
    public String userMobileNo;
    public String appointmentId;

    public String getAppointmentId() {
        return appointmentId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(Integer serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public long getMemberID() {
        return memberId;
    }

    public void setMemberID(Integer memberId) {
        this.memberId = memberId;
    }

    public long getVitalID() {
        return vitalID;
    }

    public void setVitalID(long vitalID) {
        this.vitalID = vitalID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }

    public Integer getServiceProviderDetailsID() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsID(Integer serviceProviderDetailsID) {
        this.serviceProviderDetailsId = serviceProviderDetailsID;
    }
}
