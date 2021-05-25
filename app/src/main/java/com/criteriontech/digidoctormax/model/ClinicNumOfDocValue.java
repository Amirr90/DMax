package com.criteriontech.digidoctormax.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.criteriontech.digidoctormax.utils.AppUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ClinicNumOfDocValue {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("departmentName")
    @Expose
    public String departmentName;
    @SerializedName("yearOfExperience")
    @Expose
    public String yearOfExperience;
    @SerializedName("drFee")
    @Expose
    public Integer drFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDepartmentName() {
        return departmentName+", "+ yearOfExperience;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(String yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getDrFee() {
        return AppUtils.getCurrencyFormat(drFee);
    }

    public void setDrFee(Integer drFee) {
        this.drFee = drFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClinicNumOfDocValue that = (ClinicNumOfDocValue) o;
        return name.equals(that.name) &&
                //mobileNo.equals(that.mobileNo) &&
                departmentName.equals(that.departmentName) &&
                yearOfExperience.equals(that.yearOfExperience) &&
                drFee.equals(that.drFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mobileNo, departmentName, yearOfExperience, drFee);
    }

    public static DiffUtil.ItemCallback<ClinicNumOfDocValue> itemCallback=new DiffUtil.ItemCallback<ClinicNumOfDocValue>() {
        @Override
        public boolean areItemsTheSame(@NonNull ClinicNumOfDocValue oldItem, @NonNull ClinicNumOfDocValue newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ClinicNumOfDocValue oldItem, @NonNull ClinicNumOfDocValue newItem) {
            return oldItem.equals(newItem);
        }
    };
}
