package com.criteriontech.digidoctormax.model;

public class BookAppointment2 {
    private String memberId;
    private String patientName;
    private String mobileNo;
    private String guardianTypeId;
    private String guardianName;
    private String stateID;
    private String cityID;
    private String address;
    private String pincode;
    private String serviceProviderDetailsId;
    private String appointDate;
    private String appointTime;
    private String isEraUser;
    private String problemName;
    private String dtPaymentTable;
    private String appointmentId;
    private String dob;
    private String gender;
    private String paymentId;
    private String trxId;
    private String paymentMode;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGuardianTypeId() {
        return guardianTypeId;
    }

    public void setGuardianTypeId(String guardianTypeId) {
        this.guardianTypeId = guardianTypeId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public String getIsEraUser() {
        return isEraUser;
    }

    public void setIsEraUser(String isEraUser) {
        this.isEraUser = isEraUser;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getDtPaymentTable() {
        return dtPaymentTable;
    }

    public void setDtPaymentTable(String dtPaymentTable) {
        this.dtPaymentTable = dtPaymentTable;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }


    @Override
    public String toString() {
        return "{" +
                "memberId='" + memberId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", guardianTypeId='" + guardianTypeId + '\'' +
                ", guardianName='" + guardianName + '\'' +
                ", stateID='" + stateID + '\'' +
                ", cityID='" + cityID + '\'' +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", serviceProviderDetailsId='" + serviceProviderDetailsId + '\'' +
                ", appointDate='" + appointDate + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", isEraUser='" + isEraUser + '\'' +
                ", problemName='" + problemName + '\'' +
                ", dtPaymentTable='" + dtPaymentTable + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", trxId='" + trxId + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                '}';
    }
}
