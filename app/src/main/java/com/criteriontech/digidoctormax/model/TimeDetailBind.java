package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeDetailBind {
    @SerializedName("dayName")
    @Expose
    public String dayName;
    @SerializedName("dayId")
    @Expose
    public Integer dayId;
    @SerializedName("timeDetails")
    @Expose
    public List<TimeDetail> timeDetails = null;
    @SerializedName("duration")
    @Expose
    public Integer duration;

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public List<TimeDetail> getTimeDetails() {
        return timeDetails;
    }

    public void setTimeDetails(List<TimeDetail> timeDetails) {
        this.timeDetails = timeDetails;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
