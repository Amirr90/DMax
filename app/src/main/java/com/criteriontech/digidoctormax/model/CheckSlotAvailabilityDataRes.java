package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckSlotAvailabilityDataRes {
    @SerializedName("isAvailable")
    @Expose
    private Integer isAvailable;
    @SerializedName("slotList")
    @Expose
    private List<GetAppointmentSlotsDataRes> slotList = null;

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public List<GetAppointmentSlotsDataRes> getSlotList() {
        return slotList;
    }

    public void setSlotList(List<GetAppointmentSlotsDataRes> slotList) {
        this.slotList = slotList;
    }

}
