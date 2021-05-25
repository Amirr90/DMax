package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.SpecialityValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialityResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<SpecialityValue> responseValue = null;

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

    public List<SpecialityValue> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<SpecialityValue> responseValue) {
        this.responseValue = responseValue;
    }
}
