package com.criteriontech.digidoctormax.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.criteriontech.digidoctormax.utils.AppUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class CollectedFeeValue {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("departmentName")
    @Expose
    public String departmentName;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("totalCollected")
    @Expose
    public Integer totalCollected;
    @SerializedName("totalCollectedFees")
    @Expose
    public Integer totalCollectedFees;

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
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTotalCollected() {
        return AppUtils.getCurrencyFormat(totalCollected);
    }

    public void setTotalCollected(Integer totalCollected) {
        this.totalCollected = totalCollected;
    }

    public String getTotalCollectedFees() {

        return AppUtils.getCurrencyFormat(totalCollectedFees);
    }

    public void setTotalCollectedFees(Integer totalCollectedFees) {
        this.totalCollectedFees = totalCollectedFees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectedFeeValue that = (CollectedFeeValue) o;
        return name.equals(that.name) &&
                mobileNo.equals(that.mobileNo) &&
                departmentName.equals(that.departmentName) &&
                age.equals(that.age) &&
                totalCollected.equals(that.totalCollected) &&
                totalCollectedFees.equals(that.totalCollectedFees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mobileNo, departmentName, age, totalCollected, totalCollectedFees);
    }
    public static DiffUtil.ItemCallback<CollectedFeeValue> itemCallback=new DiffUtil.ItemCallback<CollectedFeeValue>() {
        @Override
        public boolean areItemsTheSame(@NonNull CollectedFeeValue oldItem, @NonNull CollectedFeeValue newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CollectedFeeValue oldItem, @NonNull CollectedFeeValue newItem) {
            return oldItem.equals(newItem);
        }
    };
}
