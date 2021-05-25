package com.criteriontech.digidoctormax.request;

public class VideoCall {

    String userMobileNo;
    String memberId;
    String doctorId;
    String roomName;
    String callType;
    String isEraUser;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getIsEraUser() {
        return isEraUser;
    }

    public void setIsEraUser(String isEraUser) {
        this.isEraUser = isEraUser;
    }
}
