package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.DrRegistrationValue;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrRegistrationResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("responseValue")
    @Expose
    public List<LoginValue> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<LoginValue> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<LoginValue> responseValue) {
        this.responseValue = responseValue;
    }
}
