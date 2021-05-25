package com.criteriontech.digidoctormax.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class TimeSlotDataTable {
    public String dayName;
    public Integer dayId;
    public String timeFrom;
    public String timeTo;
    public Boolean isSelected=false;
    
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

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlotDataTable that = (TimeSlotDataTable) o;
        return dayId.equals(that.dayId) &&
                timeFrom.equals(that.timeFrom) &&
                timeTo.equals(that.timeTo) &&
                isSelected.equals(that.isSelected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayId, timeFrom, timeTo, isSelected);
    }
    public static DiffUtil.ItemCallback<TimeSlotDataTable> itemCallback=new DiffUtil.ItemCallback<TimeSlotDataTable>() {
        @Override
        public boolean areItemsTheSame(@NonNull TimeSlotDataTable oldItem, @NonNull TimeSlotDataTable newItem) {
            return oldItem.getDayId().equals(newItem.getDayId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TimeSlotDataTable oldItem, @NonNull TimeSlotDataTable newItem) {
            return oldItem.equals(newItem);
        }
    };
}
