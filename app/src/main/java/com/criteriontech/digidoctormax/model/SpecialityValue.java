package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialityValue {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("specialityName")
    @Expose
    public String specialityName;
    @SerializedName("imagePath")
    @Expose
    public String imagePath;
    @SerializedName("description")
    @Expose
    public String description;

    @Override
    public String toString() {
        return specialityName;
    }

    public SpecialityValue(Integer id, String specialityName, String imagePath, String description) {
        this.id = id;
        this.specialityName = specialityName;
        this.imagePath = imagePath;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
