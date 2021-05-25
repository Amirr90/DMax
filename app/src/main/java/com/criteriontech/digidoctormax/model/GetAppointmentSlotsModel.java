package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAppointmentSlotsModel {

    boolean isBooked = false;

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    private boolean isSelected = false;

    @SerializedName("slotId")
    @Expose
    private Integer slotId;
    @SerializedName("slotType")
    @Expose
    private String slotType;
    @SerializedName("slotTime")
    @Expose
    private String slotTime;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }
}
