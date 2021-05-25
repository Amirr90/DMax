package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.OtpValue;

import java.util.List;

public class OtpResp {

    Integer responseCode;
    String responseMessage;
    List<OtpValue> responseValue;

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

    public List<OtpValue> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<OtpValue> responseValue) {
        this.responseValue = responseValue;
    }

}
