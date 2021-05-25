package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavePrescriptionValue {
    @SerializedName("appointmentId")
    @Expose
    public Integer appointmentId;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;
    @SerializedName("msgTitle")
    @Expose
    public String msgTitle;
    @SerializedName("msgBody")
    @Expose
    public String msgBody;
    @SerializedName("deviceToken")
    @Expose
    public String deviceToken;
    @SerializedName("memberName")
    @Expose
    public String memberName;
    @SerializedName("deviceType")
    @Expose
    public Integer deviceType;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("notificationType")
    @Expose
    public Integer notificationType;
    @SerializedName("isMonitoring")
    @Expose
    public Integer isMonitoring;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public Integer getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Integer notificationType) {
        this.notificationType = notificationType;
    }

    public Integer getIsMonitoring() {
        return isMonitoring;
    }

    public void setIsMonitoring(Integer isMonitoring) {
        this.isMonitoring = isMonitoring;
    }
}
