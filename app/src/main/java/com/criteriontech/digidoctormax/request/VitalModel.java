package com.criteriontech.digidoctormax.request;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class VitalModel extends BaseObservable {
    public String systolic;
    public String diastolic;
    public String pulse;
    public String rbs;
    public String spo2;
    public String rr;
    public String temp;
    public String memberId;
    public String vitalId;

    public String getVitalId() {
        return vitalId;
    }

    public void setVitalId(String vitalId) {
        this.vitalId = vitalId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Bindable
    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    @Bindable
    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    @Bindable
    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    @Bindable
    public String getRbs() {
        return rbs;
    }

    public void setRbs(String rbs) {
        this.rbs = rbs;
    }

    @Bindable
    public String getSpo2() {
        return spo2;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    @Bindable
    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    @Bindable
    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
