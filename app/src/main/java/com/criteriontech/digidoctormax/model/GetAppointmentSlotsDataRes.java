package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAppointmentSlotsDataRes {
    @SerializedName("slotId")
    @Expose
    private Integer slotId;
    @SerializedName("slotType")
    @Expose
    private String slotType;

    @Override
    public String toString() {
        return "GetAppointmentSlotsDataRes{" +
                "slotId=" + slotId +
                ", slotType='" + slotType + '\'' +
                ", slotDetails=" + slotDetails +
                '}';
    }

    @SerializedName("slotDetails")
    @Expose
    private List<GetAppointmentSlotsModel> slotDetails = null;

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

    public List<GetAppointmentSlotsModel> getSlotDetails() {
        return slotDetails;
    }

    public void setSlotDetails(List<GetAppointmentSlotsModel> slotDetails) {
        this.slotDetails = slotDetails;
    }

}
