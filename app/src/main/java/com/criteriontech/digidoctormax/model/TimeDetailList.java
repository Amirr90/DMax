package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeDetailList {
    @SerializedName("dayName")
    @Expose
    public String dayName;
    @SerializedName("timeDetails")
    @Expose
    public List<TimeDetail> timeDetails = null;

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<TimeDetail> getTimeDetails() {
        return timeDetails;
    }

    public void setTimeDetails(List<TimeDetail> timeDetails) {
        this.timeDetails = timeDetails;
    }
}
