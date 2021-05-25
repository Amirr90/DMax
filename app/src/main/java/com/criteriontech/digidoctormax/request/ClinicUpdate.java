package com.criteriontech.digidoctormax.request;

public class ClinicUpdate {
    private long id;
    private String mobileNo;
    private String address;
    private long stateID;
    private long cityID;
    private String filePath;
    private String pincode;
    private String note;
    private String clinicName;
    private long dtTable;
    private String userMobileNo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getStateID() {
        return stateID;
    }

    public void setStateID(long stateID) {
        this.stateID = stateID;
    }

    public long getCityID() {
        return cityID;
    }

    public void setCityID(long cityID) {
        this.cityID = cityID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public long getDtTable() {
        return dtTable;
    }

    public void setDtTable(long dtTable) {
        this.dtTable = dtTable;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }
}
