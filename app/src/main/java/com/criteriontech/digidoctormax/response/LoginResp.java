package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.LoginValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("isCovid")
    @Expose
    public String isCovid;
    @SerializedName("responseValue")
    @Expose
    public List<LoginValue> responseValue;

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

    public Object getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsCovid() {
        return isCovid;
    }

    public void setIsCovid(String isCovid) {
        this.isCovid = isCovid;
    }

    public List<LoginValue> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<LoginValue> responseValue) {
        this.responseValue = responseValue;
    }
}
