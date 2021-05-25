package com.criteriontech.digidoctormax.model;

import java.util.ArrayList;
import java.util.List;

public class ChatResponse {

    Integer responseCode;
    String responseMessage;
    List<ChatModel> responseValue = new ArrayList<>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<ChatModel> getResponseValue() {
        return responseValue;
    }

}
