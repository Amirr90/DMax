package com.criteriontech.digidoctormax.model;

public class CalendarModel {

    String date;
    String day;
    String dateSend;
    boolean isAvailable;

    public CalendarModel(String date, String day, String dateSend, boolean position) {
        this.date = date;
        this.day = day;
        this.dateSend = dateSend;
        this.isAvailable = position;
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getDateSend() {
        return dateSend;
    }

}
