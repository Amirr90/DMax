package com.criteriontech.digidoctormax.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoCallRes {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("memberTwilioAccessToken")
    @Expose
    public String memberTwilioAccessToken;
    @SerializedName("doctorTwilioAccessToken")
    @Expose
    public String doctorTwilioAccessToken;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getMemberTwilioAccessToken() {
        return memberTwilioAccessToken;
    }

    public String getDoctorTwilioAccessToken() {
        return doctorTwilioAccessToken;
    }
}
