package com.criteriontech.digidoctormax.model;

public class ImageModel {
    String filePath;
    String fileType;

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
