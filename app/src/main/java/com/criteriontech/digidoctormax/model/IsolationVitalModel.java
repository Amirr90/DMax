package com.criteriontech.digidoctormax.model;

public class IsolationVitalModel {
    String key;
    String value;

    public IsolationVitalModel(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public IsolationVitalModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
