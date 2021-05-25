package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.ClinicDashboardValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClinicDashboardResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<ClinicDashboardValue> responseValue = null;

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

    public List<ClinicDashboardValue> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<ClinicDashboardValue> responseValue) {
        this.responseValue = responseValue;
    }
}
