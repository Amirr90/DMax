package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddVitalValue {
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("hiddenDate")
    @Expose
    public String hiddenDate;
    @SerializedName("type")
    @Expose
    public Integer type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHiddenDate() {
        return hiddenDate;
    }

    public void setHiddenDate(String hiddenDate) {
        this.hiddenDate = hiddenDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
