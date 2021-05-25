package com.criteriontech.digidoctormax.model;

import java.util.List;

public class InvestigationRes {
    String responseMessage;
    int responseCode;
    List<InvestigationModel> responseValue;

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public List<InvestigationModel> getResponseValue() {
        return responseValue;
    }
}
