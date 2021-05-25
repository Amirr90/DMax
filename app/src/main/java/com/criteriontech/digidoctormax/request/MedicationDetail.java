package com.criteriontech.digidoctormax.request;

import org.json.JSONArray;

public class MedicationDetail {
    private Integer appointmentId;
    private String userMobileNo;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }
}
