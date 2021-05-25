package com.criteriontech.digidoctormax.model;

public class SelectVitalCategoryModel {
    private String title;
    private int image;
    private boolean isSelected;
    private String color;
    private String vitalId;

    public SelectVitalCategoryModel(String title, int image, boolean isSelected, String color, String vitalId) {
        this.title = title;
        this.image = image;
        this.isSelected = isSelected;
        this.color = color;
        this.vitalId = vitalId;
    }


    public String getVitalId() {
        return vitalId;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getColor() {
        return color;
    }
}
