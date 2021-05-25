package com.criteriontech.digidoctormax.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class NoOfPatientValue {
    @SerializedName("expiredStatus")
    @Expose
    public Integer expiredStatus;

    @SerializedName("memberId")
    @Expose
    public Integer memberId;
    @SerializedName("appointmentId")
    @Expose
    public Integer appointmentId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("appointmentDate")
    @Expose
    public String appointmentDate;
    @SerializedName("appointmentTime")
    @Expose
    public String appointmentTime;
    @SerializedName("isPrescribed")
    @Expose
    public Boolean isPrescribed;
    @SerializedName("userMobileNo")
    @Expose
    public String userMobileNo;


    public Integer getExpiredStatus() {
        return expiredStatus;
    }

    public void setExpiredStatus(Integer expiredStatus) {
        this.expiredStatus = expiredStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDetail() {
        return gender + ", " + age;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Boolean getPrescribed() {
        return isPrescribed;
    }

    public void setPrescribed(Boolean prescribed) {
        isPrescribed = prescribed;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoOfPatientValue that = (NoOfPatientValue) o;
        return name.equals(that.name) &&
                gender.equals(that.gender) &&
                age.equals(that.age) &&
                address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, age, address);
    }

    public static DiffUtil.ItemCallback<NoOfPatientValue> itemCallback = new DiffUtil.ItemCallback<NoOfPatientValue>() {
        @Override
        public boolean areItemsTheSame(@NonNull NoOfPatientValue oldItem, @NonNull NoOfPatientValue newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoOfPatientValue oldItem, @NonNull NoOfPatientValue newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public String toString() {
        return "{" +
                "isExpired=" + expiredStatus +
                ", memberId=" + memberId +
                ", appointmentId=" + appointmentId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", isPrescribed=" + isPrescribed +
                ", userMobileNo='" + userMobileNo + '\'' +
                '}';
    }
}
