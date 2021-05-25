package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonitorResponse {

    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("isMonitoring")
    @Expose
    private Integer isMonitoring;
    @SerializedName("userMobileNo")
    @Expose
    private String userMobileNo;

    @Override
    public String toString() {
        return "MonitorResponse{" +
                "appointmentId=" + appointmentId +
                ", isMonitoring=" + isMonitoring +
                ", userMobileNo='" + userMobileNo + '\'' +
                '}';
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getIsMonitoring() {
        return isMonitoring;
    }

    public void setIsMonitoring(Integer isMonitoring) {
        this.isMonitoring = isMonitoring;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }
}
